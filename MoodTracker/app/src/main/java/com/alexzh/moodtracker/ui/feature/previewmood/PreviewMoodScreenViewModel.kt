package com.alexzh.moodtracker.ui.feature.previewmood

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.domain.datasource.MoodRecordDataSource
import com.alexzh.moodtracker.domain.model.MoodRecordWithActions
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.model.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PreviewMoodScreenViewModel(
    private val moodRecordDataSource: MoodRecordDataSource,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    
    private val _events = Channel<UiEvent>(Channel.UNLIMITED)
    val events = _events.receiveAsFlow()
    
    private val moodId: Long = savedStateHandle.get<Long>("moodId") ?: 0L
    
    val uiState: StateFlow<PreviewMoodScreenUiState> = flow {
        if (moodId != 0L) {
            emit(PreviewMoodScreenUiState(isLoading = true))
            
            val moodRecord = moodRecordDataSource.getMoodRecordById(moodId)
            emit(
                if (moodRecord != null) {
                    PreviewMoodScreenUiState(
                        isLoading = false,
                        moodId = moodRecord.id,
                        mood = LocalizedMood.fromHappiness(moodRecord.happiness),
                        dateTime = moodRecord.date,
                        actions = mapActionsToUi(moodRecord),
                        note = moodRecord.note
                    )
                } else {
                    PreviewMoodScreenUiState(isLoading = false)
                }
            )
        } else {
            emit(PreviewMoodScreenUiState())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PreviewMoodScreenUiState()
    )
    
    fun onEvent(event: PreviewMoodScreenEvent) {
        when (event) {
            is PreviewMoodScreenEvent.OnDeleteMood -> deleteMood()
        }
    }
    
    private fun deleteMood() {
        viewModelScope.launch {
            moodRecordDataSource.deleteMoodRecord(moodId)
            _events.send(UiEvent.NavigateBack)
        }
    }
    
    private fun mapActionsToUi(moodRecord: MoodRecordWithActions): List<ActionItem> {
        return moodRecord.actions.map { action ->
            ActionItem(
                id = action.id,
                name = action.title
            )
        }
    }
}