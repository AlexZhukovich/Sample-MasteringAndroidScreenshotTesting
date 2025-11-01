package com.alexzh.moodtracker.ui.feature.statistics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.domain.model.IconShape
import com.alexzh.moodtracker.ui.designsystem.bars.TopAppBar
import com.alexzh.moodtracker.ui.designsystem.chart.ActionImpactData
import com.alexzh.moodtracker.ui.designsystem.chart.ActionToHappinessChart
import com.alexzh.moodtracker.ui.designsystem.chart.AverageDailyMoodChart
import com.alexzh.moodtracker.ui.designsystem.chart.ChartDataItem
import com.alexzh.moodtracker.ui.designsystem.empty.EmptyState
import com.alexzh.moodtracker.ui.designsystem.section.CardSection
import com.alexzh.moodtracker.ui.designsystem.selector.PeriodSelector
import com.alexzh.moodtracker.ui.feature.statistics.components.StatisticsEmptyStateAnimatedIcon
import com.alexzh.moodtracker.ui.navigation.AppNavigationItems
import com.alexzh.moodtracker.ui.navigation.AppNavigationSuiteScaffold
import com.alexzh.moodtracker.ui.theme.AppTheme
import java.time.LocalDate

@Composable
fun StatisticsScreen(
    viewModel: StatisticsScreenViewModel,
    onNavigateToHome: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    StatisticsScreenContent(
        uiState = uiState.value,
        onNavigateToHome = onNavigateToHome,
        onPreviousMonth = { viewModel.onEvent(StatisticsScreenEvent.OnPreviousMonth) },
        onNextMonth = { viewModel.onEvent(StatisticsScreenEvent.OnNextMonth) }
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun StatisticsScreenContent(
    uiState: StatisticsScreenUiState,
    onNavigateToHome: () -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompactLayout = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND).not()
    AppNavigationSuiteScaffold(
        selectedItem = AppNavigationItems.STATISTICS,
        onNavigateToHome = onNavigateToHome,
        onNavigateToStatistics = { }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = stringResource(R.string.statisticsScreen_title),
                    actions = {
                        PeriodSelector(
                            label = uiState.selectedDateRange.title,
                            onPrevious = onPreviousMonth,
                            onNext = onNextMonth,
                            previousContentDescription = stringResource(R.string.common_previousMonth_contentDescription),
                            nextEnabledContentDescription = stringResource(R.string.common_nextMonth_contentDescription)
                        )
                    }
                )
            }
        ) { innerPadding ->
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
                uiState.actionImpactChartData.isEmpty() && uiState.averageDailyMoodChartData.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        EmptyState(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            icon = { StatisticsEmptyStateAnimatedIcon() },
                            title = stringResource(R.string.statisticsScreen_emptyState_title),
                            text = stringResource(R.string.statisticsScreen_emptyState_label)
                        )
                    }
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AverageDailyMoodSection(
                            averageDailyMoodChartData = uiState.averageDailyMoodChartData,
                            iconShape = uiState.iconShape
                        )
                        if (!uiState.actionImpactChartData.isEmpty()) {
                            ActionToHappinessSection(
                                actionImpactData = ActionImpactData(
                                    positiveImpactData = uiState.actionImpactChartData.positiveImpact,
                                    negativeImpactData = uiState.actionImpactChartData.negativeImpact,
                                ),
                                isCompactLayout = isCompactLayout
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AverageDailyMoodSection(
    averageDailyMoodChartData: AverageDailyMoodChartData,
    iconShape: IconShape
) {
    CardSection(
        modifier = Modifier.fillMaxWidth(),
        title = stringResource(R.string.statisticsScreen_averageDailyMoodSection_label),
    ) {
        AverageDailyMoodChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            data = averageDailyMoodChartData.data,
            iconShape = iconShape,
            scrollPosition = averageDailyMoodChartData.scrollPosition
        )
    }
}

@Composable
private fun ActionToHappinessSection(
    actionImpactData: ActionImpactData,
    isCompactLayout: Boolean
) {
    if (isCompactLayout) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardSection(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.statisticsScreen_actionImpactOverviewSection_label)
            ) {
                ActionToHappinessChart(
                    modifier = Modifier.fillMaxWidth(),
                    data = ActionImpactData(
                        positiveImpactData = actionImpactData.positiveImpactData,
                        negativeImpactData = actionImpactData.negativeImpactData
                    )
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (actionImpactData.positiveImpactData.isNotEmpty()) {
                Box(modifier = Modifier.weight(1f)) {
                    CardSection(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(R.string.statisticsScreen_positiveImpactActionsSection_label)
                    ) {
                        ActionToHappinessChart(
                            modifier = Modifier.fillMaxWidth(),
                            data = ActionImpactData(
                                positiveImpactData = actionImpactData.positiveImpactData,
                                negativeImpactData = emptyList()
                            )
                        )
                    }
                }
            }

            if (actionImpactData.negativeImpactData.isNotEmpty()) {
                Box(modifier = Modifier.weight(1f)) {
                    CardSection(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(R.string.statisticsScreen_negativeImpactActionsSection_label)
                    ) {
                        ActionToHappinessChart(
                            modifier = Modifier.fillMaxWidth(),
                            data = ActionImpactData(
                                positiveImpactData = emptyList(),
                                negativeImpactData = actionImpactData.negativeImpactData
                            )
                        )
                    }
                }
            }

            if (actionImpactData.isEmpty()) {
                Box(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(name = "Phone - Light", device = PHONE, showBackground = true)
@Preview(name = "Phone - Dark", device = PHONE, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Tablet - Light", device = PIXEL_TABLET, showBackground = true)
@Preview(name = "Tablet - Dark", device = PIXEL_TABLET, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview_StatisticsScreenContent(
    @PreviewParameter(StatisticsScreenUiStateProvider::class) uiState: StatisticsScreenUiState
) {
    AppTheme {
        StatisticsScreenContent(
            uiState = uiState,
            onNavigateToHome = { },
            onPreviousMonth = { },
            onNextMonth = { }
        )
    }
}

private val sampleAverageDailyMoodData = listOf(
    ChartDataItem(label = "1", value = 3.5f),
    ChartDataItem(label = "2", value = 2.0f),
    ChartDataItem(label = "3", value = 4.5f),
    ChartDataItem(label = "4", value = 3.0f),
    ChartDataItem(label = "5", value = 3.0f),
    ChartDataItem(label = "6", value = 4.0f),
    ChartDataItem(label = "7", value = 4.5f),
    ChartDataItem(label = "8", value = 2.5f),
    ChartDataItem(label = "9", value = 4.0f),
    ChartDataItem(label = "10", value = 3.5f),
    ChartDataItem(label = "11", value = 2.0f),
    ChartDataItem(label = "12", value = 4.5f),
    ChartDataItem(label = "13", value = 2.0f),
    ChartDataItem(label = "14", value = 4.5f),
    ChartDataItem(label = "15", value = 3.0f),
    ChartDataItem(label = "16", value = 3.5f),
    ChartDataItem(label = "17", value = 2.0f),
    ChartDataItem(label = "18", value = 4.5f),
    ChartDataItem(label = "19", value = 2.5f),
    ChartDataItem(label = "20", value = 4.0f),
    ChartDataItem(label = "21", value = 4.5f),
    ChartDataItem(label = "22", value = 3.0f),
    ChartDataItem(label = "23", value = 3.0f),
    ChartDataItem(label = "24", value = 5.0f),
    ChartDataItem(label = "25", value = 3.5f),
    ChartDataItem(label = "26", value = 2.0f),
    ChartDataItem(label = "27", value = 3.5f),
    ChartDataItem(label = "28", value = 4.0f),
    ChartDataItem(label = "29", value = 4.5f),
    ChartDataItem(label = "30", value = 3.5f),
    ChartDataItem(label = "31", value = 2.7f)
)

private val samplePositiveImpactData = listOf(
    ChartDataItem(label = "Meeting", value = 4.75f),
    ChartDataItem(label = "Listening to music", value = 4.0f),
    ChartDataItem(label = "Drawing", value = 3.8f),
    ChartDataItem(label = "Watching movies", value = 3.6f)
)

private val sampleNegativeImpactData = listOf(
    ChartDataItem(label = "Family time", value = 3.2f),
    ChartDataItem(label = "Reading", value = 2.75f),
    ChartDataItem(label = "Party time", value = 2.75f),
    ChartDataItem(label = "Gym Workout", value = 2.5f),
    ChartDataItem(label = "Walking", value = 2.3f)
)

class StatisticsScreenUiStateProvider : PreviewParameterProvider<StatisticsScreenUiState> {
    override val values: Sequence<StatisticsScreenUiState>
        get() = sequenceOf(
            StatisticsScreenUiState(
                isLoading = false,
                selectedDateRange = SelectedDateRangeData(
                    title = "August 2025",
                    startDate = LocalDate.of(2025, 8, 1),
                    endDate = LocalDate.of(2025, 8, 31)
                ),
                averageDailyMoodChartData = AverageDailyMoodChartData(),
                actionImpactChartData = ActionImpactChartData(),
                iconShape = IconShape.CIRCLE
            ),
            StatisticsScreenUiState(
                isLoading = false,
                selectedDateRange = SelectedDateRangeData(
                    title = "August 2025",
                    startDate = LocalDate.of(2025, 8, 1),
                    endDate = LocalDate.of(2025, 8, 31)
                ),
                averageDailyMoodChartData = AverageDailyMoodChartData(
                    data = sampleAverageDailyMoodData,
                    scrollPosition = 10
                ),
                actionImpactChartData = ActionImpactChartData(
                    positiveImpact = samplePositiveImpactData,
                    negativeImpact = sampleNegativeImpactData
                ),
                iconShape = IconShape.CIRCLE
            ),
            StatisticsScreenUiState(
                isLoading = false,
                selectedDateRange = SelectedDateRangeData(
                    title = "August 2025",
                    startDate = LocalDate.of(2025, 8, 1),
                    endDate = LocalDate.of(2025, 8, 31)
                ),
                averageDailyMoodChartData = AverageDailyMoodChartData(
                    data = sampleAverageDailyMoodData.take(15),
                    scrollPosition = 0
                ),
                iconShape = IconShape.ROUNDED_SQUARE
            )
        )
}