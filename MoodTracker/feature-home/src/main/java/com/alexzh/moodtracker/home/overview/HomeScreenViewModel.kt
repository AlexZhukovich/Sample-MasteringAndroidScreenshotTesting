package com.alexzh.moodtracker.home.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.core.domain.datasource.MoodRecordDataSource
import com.alexzh.moodtracker.core.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.core.domain.model.MoodRecordWithActions
import com.alexzh.moodtracker.core.domain.provider.DateProvider
import com.alexzh.moodtracker.core.domain.resolver.ImagePathResolver
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModel(
    private val moodRecordDataSource: MoodRecordDataSource,
    private val imagePathResolver: ImagePathResolver,
    settingsDataSource: SettingsDataSource,
    dateProvider: DateProvider
) : ViewModel() {

    private val selectedDate = MutableStateFlow(dateProvider.getCurrentDate())
    private val selectedMoodItem = MutableStateFlow<MoodItem?>(null)
    private val currentDate: StateFlow<LocalDate> = dateProvider.currentDateFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, dateProvider.getCurrentDate())
    
    private val moodItemsFlow = selectedDate
        .flatMapLatest { date -> 
            moodRecordDataSource.getMoodRecordsForDate(date)
                .map { records -> records.map { mapToMoodItem(it) } }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val uiState: StateFlow<HomeScreenUiState> = combine(
        selectedDate,
        currentDate,
        moodItemsFlow,
        selectedMoodItem,
        settingsDataSource.getIconShape()
    ) { selectedDate, currentDate, moodItems, selectedMoodItem, iconShape ->
        HomeScreenUiState(
            selectedDate = selectedDate,
            currentDate = currentDate,
            moodItems = moodItems,
            selectedMoodItem = selectedMoodItem,
            isLoading = false,
            iconShape = iconShape
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = HomeScreenUiState(
            selectedDate = dateProvider.getCurrentDate(),
            currentDate = dateProvider.getCurrentDate()
        )
    )

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnChangeData -> updateSelectedDate(event.date)
            is HomeScreenEvent.OnSelectMoodItem -> selectMoodItem(event.moodId)
            is HomeScreenEvent.OnClearSelection -> clearSelection()
            is HomeScreenEvent.OnDeleteMood -> deleteMood()
        }
    }

    private fun updateSelectedDate(selectedDate: LocalDate) {
        this.selectedDate.value = selectedDate
        clearSelection()
    }

    private fun selectMoodItem(moodId: Long) {
        val moodItem = uiState.value.moodItems.find { it.id == moodId }
        selectedMoodItem.value = moodItem
    }

    private fun clearSelection() {
        selectedMoodItem.value = null
    }

    private fun deleteMood() {
        val currentSelectedMood = selectedMoodItem.value
        if (currentSelectedMood != null) {
            viewModelScope.launch {
                moodRecordDataSource.deleteMoodRecord(currentSelectedMood.id)
                clearSelection()
            }
        }
    }

    private fun mapToMoodItem(moodRecord: MoodRecordWithActions): MoodItem {
        val actions = moodRecord.actions.map { action ->
            ActionItem(
                id = action.id,
                name = action.title
            )
        }
        val photoUris = moodRecord.photos.map { moodImage ->
            imagePathResolver.resolveImageUri(moodImage.photoPath)
        }
        return MoodItem(
            id = moodRecord.id,
            mood = LocalizedMood.fromHappiness(moodRecord.happiness),
            date = moodRecord.date,
            note = moodRecord.note,
            actions = actions,
            photos = photoUris
        )
    }
}