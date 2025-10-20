package com.alexzh.moodtracker.ui.feature.editmood

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.data.database.mood.MoodRecordEntity
import com.alexzh.moodtracker.domain.datasource.ActionCategoryDataSource
import com.alexzh.moodtracker.domain.datasource.MoodRecordDataSource
import com.alexzh.moodtracker.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.domain.model.ActionCategoryDetails
import com.alexzh.moodtracker.domain.provider.DateProvider
import com.alexzh.moodtracker.domain.resolver.ImagePathResolver
import com.alexzh.moodtracker.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.model.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class EditMoodScreenViewModel(
    private val moodRecordDataSource: MoodRecordDataSource,
    private val imagePathResolver: ImagePathResolver,
    settingsDataSource: SettingsDataSource,
    actionCategoryDataSource: ActionCategoryDataSource,
    dateProvider: DateProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _events = Channel<UiEvent>(Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

    val moodId: Long = savedStateHandle.get<Long>("moodId") ?: 0L
    private val preselectedMood: LocalizedMood? = savedStateHandle.get<LocalizedMood>("preselectedMood")

    private val actionCategoriesFlow = actionCategoryDataSource.getActionCategoryDetails()
        .map { categoryDetails -> mapActionCategoriesToUi(categoryDetails) }
        .stateIn(viewModelScope, SharingStarted.Lazily, mapOf())
    
    private val _uiState = MutableStateFlow(
        EditMoodScreenUiState(
            isNewMood = moodId == 0L,
            selectedDate = dateProvider.getCurrentDate(),
            selectedTime = LocalTime.now(),
            moodItems = SelectableMoodItems(
                selectedMood = preselectedMood
            )
        )
    )
    
    val uiState: StateFlow<EditMoodScreenUiState> = combine(
        actionCategoriesFlow,
        settingsDataSource.getIconShape(),
        _uiState
    ) { actionCategories, iconShape, currentUiState ->
        currentUiState.copy(
            actionCategoryItems = currentUiState.actionCategoryItems.copy(
                userActivityCategory = actionCategories
            ),
            iconShape = iconShape
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = EditMoodScreenUiState(
            isNewMood = moodId == 0L,
            selectedDate = dateProvider.getCurrentDate(),
            selectedTime = LocalTime.now(),
            moodItems = SelectableMoodItems(
                selectedMood = preselectedMood
            )
        )
    )
    
    init {
        if (moodId != 0L) {
            viewModelScope.launch {
                val moodRecord = moodRecordDataSource.getMoodRecordById(moodId)
                if (moodRecord != null) {
                    val photoUris = moodRecord.photos.map { moodImage ->
                        imagePathResolver.resolveImageUri(moodImage.photoPath)
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            isNewMood = false,
                            selectedDate = moodRecord.date.toLocalDate(),
                            selectedTime = moodRecord.date.toLocalTime(),
                            note = moodRecord.note,
                            photos = photoUris,
                            isMoodDataLoaded = true,
                            moodItems = SelectableMoodItems(
                                selectedMood = LocalizedMood.fromHappiness(moodRecord.happiness)
                            ),
                            actionCategoryItems = currentState.actionCategoryItems.copy(
                                selectedUserActivityIds = moodRecord.actions.map { it.id }
                            )
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: EditMoodScreenEvent) {
        when (event) {
            is EditMoodScreenEvent.OnMoodChange -> updateMood(event.mood)
            is EditMoodScreenEvent.OnNoteChange -> updateNote(event.note)
            is EditMoodScreenEvent.OnActionChange -> updateAction(event.actionItem)
            is EditMoodScreenEvent.OnDateChange -> updateDate(event.date)
            is EditMoodScreenEvent.OnTimeChange -> updateTime(event.time)
            is EditMoodScreenEvent.OnPhotoChange -> updatePhoto(event.action)
            is EditMoodScreenEvent.OnSave -> saveMood()
        }
    }

    private fun updatePhoto(action: PhotoAction) {
        when (action) {
            is PhotoAction.Add -> addPhoto(action.photoUri)
            is PhotoAction.Remove -> removePhoto(action.photoIndex)
        }
    }

    private fun addPhoto(photoUri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(
                photos = currentState.photos + photoUri
            )
        }
    }

    private fun removePhoto(photoIndex: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                photos = currentState.photos.filterIndexed { index, _ -> index != photoIndex }
            )
        }
    }
    
    private fun updateMood(mood: LocalizedMood) {
        _uiState.update { currentState ->
            currentState.copy(
                moodItems = currentState.moodItems.copy(
                    selectedMood = mood
                )
            )
        }
    }
    
    private fun updateNote(note: String) {
        _uiState.update { currentState ->
            currentState.copy(note = note)
        }
    }
    
    private fun updateAction(actionItem: ActionItem) {
        _uiState.update { currentState ->
            val currentIds = currentState.actionCategoryItems.selectedUserActivityIds
            val newIds = if (currentIds.contains(actionItem.id)) {
                currentIds - actionItem.id
            } else {
                currentIds + actionItem.id
            }
            currentState.copy(
                actionCategoryItems = currentState.actionCategoryItems.copy(
                    selectedUserActivityIds = newIds
                )
            )
        }
    }
    
    private fun updateDate(date: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(selectedDate = date)
        }
    }
    
    private fun updateTime(time: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(selectedTime = time)
        }
    }
    
    private fun saveMood() {
        val currentState = _uiState.value
        if (!currentState.canSave || currentState.isLoading) {
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val moodRecord = MoodRecordEntity(
                    id = moodId,
                    happiness = currentState.moodItems.selectedMood?.happiness ?: 0f,
                    date = currentState.selectedDate.atTime(currentState.selectedTime),
                    note = currentState.note
                )

                if (moodId == 0L) {
                    moodRecordDataSource.insertMoodRecordWithActions(
                        moodRecord = moodRecord,
                        actionIds = currentState.actionCategoryItems.selectedUserActivityIds,
                        photoUris = currentState.photos
                    )
                } else {
                    moodRecordDataSource.updateMoodRecordWithActions(
                        moodRecord = moodRecord,
                        actionIds = currentState.actionCategoryItems.selectedUserActivityIds,
                        photoUris = currentState.photos
                    )
                }

                _events.send(UiEvent.NavigateBack)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
    
    private fun mapActionCategoriesToUi(
        categoryDetails: List<ActionCategoryDetails>
    ): Map<ActionCategoryItem, List<ActionItem>> {
        return categoryDetails
            .filter { it.actions.isNotEmpty() }
            .associate { categoryDetail ->
                val categoryItem = ActionCategoryItem(
                    id = categoryDetail.id,
                    name = categoryDetail.name
                )
                val actionItems = categoryDetail.actions.map { action ->
                    ActionItem(
                        id = action.id,
                        name = action.title
                    )
                }
                categoryItem to actionItems
            }
    }
}