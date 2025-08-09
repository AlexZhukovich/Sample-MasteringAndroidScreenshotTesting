package com.alexzh.moodtracker.ui.feature.statistics

import com.alexzh.moodtracker.ui.designsystem.chart.ChartDataItem
import java.time.LocalDate

data class StatisticsScreenUiState(
    val isLoading: Boolean = false,
    val selectedDateRange: SelectedDateRangeData,
    val averageDailyMoodChartData: AverageDailyMoodChartData = AverageDailyMoodChartData(),
    val actionToHappinessChartData: ActionToHappinessChartData = ActionToHappinessChartData()
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

data class ActionToHappinessChartData(
    val data: List<ChartDataItem> = emptyList()
)