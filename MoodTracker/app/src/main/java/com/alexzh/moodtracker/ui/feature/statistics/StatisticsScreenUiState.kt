package com.alexzh.moodtracker.ui.feature.statistics

import com.alexzh.moodtracker.ui.designsystem.chart.ChartDataItem
import java.time.LocalDate

data class StatisticsScreenUiState(
    val isLoading: Boolean = false,
    val selectedDateRange: SelectedDateRangeData,
    val averageDailyMoodChartData: AverageDailyMoodChartData = AverageDailyMoodChartData(),
    val actionImpactChartData: ActionImpactChartData = ActionImpactChartData()
)

data class SelectedDateRangeData(
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)

data class AverageDailyMoodChartData(
    val data: List<ChartDataItem> = emptyList(),
    val scrollPosition: Int = 0
)

data class ActionImpactChartData(
    val positiveImpact: List<ChartDataItem> = emptyList(),
    val negativeImpact: List<ChartDataItem> = emptyList()
)