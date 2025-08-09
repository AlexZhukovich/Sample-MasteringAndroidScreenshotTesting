package com.alexzh.moodtracker.ui.feature.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.domain.datasource.MoodRecordDataSource
import com.alexzh.moodtracker.domain.provider.DateProvider
import com.alexzh.moodtracker.ui.designsystem.chart.ChartDataItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalCoroutinesApi::class)
class StatisticsScreenViewModel(
    private val moodRepository: MoodRecordDataSource,
    private val dateProvider: DateProvider,
): ViewModel() {

    companion object {
        private const val DATE_FORMATTER_PATTERN = "MMMM yyyy"
    }

    private val selectedMonth = MutableStateFlow(dateProvider.getCurrentDate())
    
    private val selectedDateRangeFlow = selectedMonth
        .flatMapLatest { date ->
            flow {
                val startOfMonth = date.withDayOfMonth(1)
                val endOfMonth = date.withDayOfMonth(date.lengthOfMonth())
                val formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN)
                emit(SelectedDateRangeData(
                    title = date.format(formatter),
                    startDate = startOfMonth,
                    endDate = endOfMonth
                ))
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, getInitialDateRange())
    
    private val averageDailyMoodDataFlow = selectedDateRangeFlow
        .flatMapLatest { dateRange ->
            flow {
                val moodData = moodRepository.getAverageDayToMoodHappiness(
                    startDate = dateRange.startDate,
                    endDate = dateRange.endDate
                )
                
                val moodDataMap = moodData.associateBy { it.date }
                val chartData = mutableListOf<ChartDataItem>()
                var currentDate = dateRange.startDate
                
                while (!currentDate.isAfter(dateRange.endDate)) {
                    val moodForDate = moodDataMap[currentDate]
                    chartData.add(
                        ChartDataItem(
                            label = currentDate.dayOfMonth.toString(),
                            value = moodForDate?.happiness ?: 0f
                        )
                    )
                    currentDate = currentDate.plusDays(1)
                }

                emit(AverageDailyMoodChartData(
                    data = chartData,
                    scrollPosition = calculateScrollPosition()
                ))
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, AverageDailyMoodChartData())

    private val actionToHappinessDataFlow = selectedDateRangeFlow
        .flatMapLatest { dateRange ->
            flow {
                val actionData = moodRepository.getAverageActionToMoodHappiness(
                    startDate = dateRange.startDate,
                    endDate = dateRange.endDate
                )
                
                val chartData = actionData.map { actionToHappiness ->
                    ChartDataItem(
                        label = actionToHappiness.action,
                        value = actionToHappiness.happiness
                    )
                }
                
                emit(ActionToHappinessChartData(data = chartData))
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, ActionToHappinessChartData())
    
    val uiState: StateFlow<StatisticsScreenUiState> = combine(
        selectedDateRangeFlow,
        averageDailyMoodDataFlow,
        actionToHappinessDataFlow
    ) { selectedDateRange, averageDailyMoodChart, actionToHappinessChart ->
        StatisticsScreenUiState(
            isLoading = false,
            selectedDateRange = selectedDateRange,
            averageDailyMoodChartData = averageDailyMoodChart,
            actionToHappinessChartData = actionToHappinessChart
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = StatisticsScreenUiState(
            isLoading = true,
            selectedDateRange = getInitialDateRange()
        )
    )

    fun onEvent(event: StatisticsScreenEvent) {
        when (event) {
            StatisticsScreenEvent.OnPreviousMonth -> updateSelectedMonth(-1)
            StatisticsScreenEvent.OnNextMonth -> updateSelectedMonth(1)
        }
    }

    private fun updateSelectedMonth(monthDelta: Int) {
        val currentMonth = selectedMonth.value
        selectedMonth.value = currentMonth.plusMonths(monthDelta.toLong())
    }

    private fun getInitialDateRange(): SelectedDateRangeData {
        val currentDate = dateProvider.getCurrentDate()
        val startOfMonth = currentDate.withDayOfMonth(1)
        val endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN)
        return SelectedDateRangeData(
            title = currentDate.format(formatter),
            startDate = startOfMonth,
            endDate = endOfMonth
        )
    }

    private fun calculateScrollPosition(): Int {
        val currentDate = dateProvider.getCurrentDate()
        val selectedMonthDate = selectedMonth.value
        val isCurrentMonth = currentDate.year == selectedMonthDate.year &&
                currentDate.month == selectedMonthDate.month

        val scrollPosition = if (isCurrentMonth) {
            maxOf(0, currentDate.dayOfMonth - 2)
        } else {
            0
        }
        return scrollPosition
    }
}