package com.alexzh.moodtracker.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.common.ui.model.LocalizedActionNameProvider
import com.alexzh.moodtracker.core.domain.datasource.MoodRecordDataSource
import com.alexzh.moodtracker.core.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.core.domain.provider.DateProvider
import com.alexzh.moodtracker.statistics.components.chart.ChartDataItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
class StatisticsScreenViewModel(
    private val actionNameProvider: LocalizedActionNameProvider,
    private val moodRepository: MoodRecordDataSource,
    settingsDataSource: SettingsDataSource,
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
                            label = NumberFormat.getInstance(Locale.getDefault()).format(currentDate.dayOfMonth),
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

    private val actionImpactDataFlow = selectedDateRangeFlow
        .flatMapLatest { dateRange ->
            flow {
                val segmentedData = moodRepository.getSegmentedActionImpact(
                    startDate = dateRange.startDate,
                    endDate = dateRange.endDate
                )

                val positiveImpact = segmentedData.positiveImpact.map { actionToHappiness ->
                    ChartDataItem(
                        label = actionToHappiness.action,
                        value = actionToHappiness.happiness
                    )
                }

                val negativeImpact = segmentedData.negativeImpact.map { actionToHappiness ->
                    ChartDataItem(
                        label = actionToHappiness.action,
                        value = actionToHappiness.happiness
                    )
                }

                emit(ActionImpactChartData(
                    positiveImpact = positiveImpact,
                    negativeImpact = negativeImpact
                ))
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, ActionImpactChartData())

    private val _uiState = MutableStateFlow(
        StatisticsScreenUiState(
            isLoading = true,
            selectedDateRange = getInitialDateRange()
        )
    )

    val uiState: StateFlow<StatisticsScreenUiState> = combine(
        selectedDateRangeFlow,
        averageDailyMoodDataFlow,
        actionImpactDataFlow,
        settingsDataSource.getIconShape(),
        _uiState
    ) { selectedDateRange, averageDailyMoodChart, actionImpactChartData, iconShape, _ ->
        StatisticsScreenUiState(
            isLoading = false,
            selectedDateRange = selectedDateRange,
            averageDailyMoodChartData = averageDailyMoodChart,
            actionImpactChartData = localizeActionImpactData(actionImpactChartData),
            iconShape = iconShape
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
            StatisticsScreenEvent.OnLocaleChange -> reloadActions()
            StatisticsScreenEvent.OnPreviousMonth -> updateSelectedMonth(-1)
            StatisticsScreenEvent.OnNextMonth -> updateSelectedMonth(1)
        }
    }

    private fun reloadActions() {
        _uiState.update { it.copy() }
    }

    private fun localizeActionImpactData(data: ActionImpactChartData): ActionImpactChartData {
        return ActionImpactChartData(
            positiveImpact = data.positiveImpact.map {
                it.copy(label = actionNameProvider.getLocalizedName(it.label))
            },
            negativeImpact = data.negativeImpact.map {
                it.copy(label = actionNameProvider.getLocalizedName(it.label))
            }
        )
    }

    private fun updateSelectedMonth(monthDelta: Int) {
        val currentMonth = selectedMonth.value
        selectedMonth.value = currentMonth.plusMonths(monthDelta.toLong())
    }

    private fun getInitialDateRange(): SelectedDateRangeData {
        val currentDate = dateProvider.getCurrentDate()
        val startOfMonth = currentDate.withDayOfMonth(1)
        val endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN, Locale.getDefault())
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