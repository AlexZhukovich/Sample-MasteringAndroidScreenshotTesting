package com.alexzh.moodtracker.statistics

import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.statistics.components.chart.ChartDataItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DecimalStyle
import java.util.Locale

data class StatisticsScreenUiState(
    val isLoading: Boolean = false,
    val selectedDateRange: SelectedDateRangeData,
    val averageDailyMoodChartData: AverageDailyMoodChartData = AverageDailyMoodChartData(),
    val actionImpactChartData: ActionImpactChartData = ActionImpactChartData(),
    val iconShape: IconShape = IconShape.CIRCLE
)

data class SelectedDateRangeData(
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    companion object {
        private const val DATE_FORMATTER_SKELETON = "yyyyMMMM"
    }

    fun formattedDate(locale: Locale = Locale.getDefault()): String {
        val pattern = android.text.format.DateFormat.getBestDateTimePattern(locale, DATE_FORMATTER_SKELETON)
        val formatter = DateTimeFormatter.ofPattern(pattern, locale)
            .withDecimalStyle(DecimalStyle.of(locale))
        return startDate.format(formatter)
    }
}

data class AverageDailyMoodChartData(
    val data: List<ChartDataItem> = emptyList(),
    val scrollPosition: Int = 0
) {
    fun isEmpty(): Boolean = data.none { it.value != 0.0f }
}

data class ActionImpactChartData(
    val positiveImpact: List<ChartDataItem> = emptyList(),
    val negativeImpact: List<ChartDataItem> = emptyList()
) {
    fun isEmpty(): Boolean = positiveImpact.isEmpty() && negativeImpact.isEmpty()
}