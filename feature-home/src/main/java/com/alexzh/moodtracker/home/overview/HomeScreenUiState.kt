package com.alexzh.moodtracker.home.overview

import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DecimalStyle
import java.time.format.FormatStyle
import java.util.Locale

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val moodItems: List<MoodItem> = emptyList(),
    val selectedDate: LocalDate,
    val currentDate: LocalDate,
    val selectedMoodItem: MoodItem? = null,
    val iconShape: IconShape = IconShape.CIRCLE
) {

    fun formattedSelectedDate(locale: Locale = Locale.getDefault()): String =
        selectedDate.format(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(locale)
                .withDecimalStyle(DecimalStyle.of(locale))
        )
}
