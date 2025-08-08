package com.alexzh.moodtracker.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.domain.datasource.MoodRecordDataSource
import com.alexzh.moodtracker.domain.model.MoodRecordWithActions
import com.alexzh.moodtracker.domain.provider.DateProvider
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.model.MoodItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModel(
    private val moodRecordDataSource: MoodRecordDataSource,
    dateProvider: DateProvider
) : ViewModel() {

    private val selectedDate = MutableStateFlow(dateProvider.getCurrentDate())
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
        moodItemsFlow
    ) { selectedDate, currentDate, moodItems ->
        HomeScreenUiState(
            selectedDate = selectedDate,
            currentDate = currentDate,
            moodItems = moodItems,
            isLoading = false
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
        }
    }
    
    private fun updateSelectedDate(selectedDate: LocalDate) {
        this.selectedDate.value = selectedDate
    }

    private fun mapToMoodItem(moodRecord: MoodRecordWithActions): MoodItem {
        val actions = moodRecord.actions.map { action ->
            ActionItem(
                id = action.id,
                name = action.title
            )
        }
        return MoodItem(
            id = moodRecord.id,
            mood = LocalizedMood.fromHappiness(moodRecord.happiness),
            date = moodRecord.date,
            note = moodRecord.note,
            actions = actions
        )
    }
}