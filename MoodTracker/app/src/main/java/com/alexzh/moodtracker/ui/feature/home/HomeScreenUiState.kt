package com.alexzh.moodtracker.ui.feature.home

import com.alexzh.moodtracker.domain.model.IconShape
import com.alexzh.moodtracker.ui.model.MoodItem
import java.time.LocalDate

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val moodItems: List<MoodItem> = emptyList(),
    val selectedDate: LocalDate,
    val currentDate: LocalDate,
    val selectedMoodItem: MoodItem? = null,
    val iconShape: IconShape = IconShape.CIRCLE
)
