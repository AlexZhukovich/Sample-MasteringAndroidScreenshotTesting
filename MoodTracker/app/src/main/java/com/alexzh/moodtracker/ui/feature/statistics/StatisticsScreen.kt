package com.alexzh.moodtracker.ui.feature.statistics

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.chart.ActionImpactData
import com.alexzh.moodtracker.ui.designsystem.chart.ActionToHappinessChart
import com.alexzh.moodtracker.ui.designsystem.chart.AverageDailyMoodChart
import com.alexzh.moodtracker.ui.designsystem.empty.EmptyState
import com.alexzh.moodtracker.ui.designsystem.section.CardSection
import com.alexzh.moodtracker.ui.feature.statistics.components.StatisticsEmptyStateAnimatedIcon
import com.alexzh.moodtracker.ui.navigation.AppNavigationItems
import com.alexzh.moodtracker.ui.navigation.AppNavigationSuiteScaffold

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
    val context = LocalContext.current
    val windowSizeClass = calculateWindowSizeClass(context as android.app.Activity)
    val windowWidthSizeClass = windowSizeClass.widthSizeClass
    AppNavigationSuiteScaffold(
        selectedItem = AppNavigationItems.STATISTICS,
        onNavigateToHome = onNavigateToHome,
        onNavigateToStatistics = { }
    ) {
        Scaffold(
            topBar = {
                StatisticsScreenTopAppBar(
                    selectedDateTitle = uiState.selectedDateRange.title,
                    onPreviousMonth = onPreviousMonth,
                    onNextMonth = onNextMonth
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
                            averageDailyMoodChartData = uiState.averageDailyMoodChartData
                        )
                        ActionToHappinessSection(
                            actionImpactData = ActionImpactData(
                                positiveImpactData = uiState.actionImpactChartData.positiveImpact,
                                negativeImpactData = uiState.actionImpactChartData.negativeImpact,
                            ),
                            windowWidthSizeClass = windowWidthSizeClass
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AverageDailyMoodSection(
    averageDailyMoodChartData: AverageDailyMoodChartData
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
            scrollPosition = averageDailyMoodChartData.scrollPosition
        )
    }
}

@Composable
private fun ActionToHappinessSection(
    actionImpactData: ActionImpactData,
    windowWidthSizeClass: WindowWidthSizeClass
) {
    when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
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
        }
        else -> {
            if (actionImpactData.isNotEmpty()) {
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatisticsScreenTopAppBar(
    modifier: Modifier = Modifier,
    selectedDateTitle: String,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(stringResource(R.string.statisticsScreen_title)) },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = onPreviousMonth) {
                    Icon(
                        painter = painterResource(R.drawable.ic_keyboard_arrow_left),
                        contentDescription = stringResource(R.string.common_previousMonth_contentDescription)
                    )
                }
                Text(
                    text = selectedDateTitle,
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = onNextMonth) {
                    Icon(
                        painter = painterResource(R.drawable.ic_keyboard_arrow_right),
                        contentDescription = stringResource(R.string.common_nextMonth_contentDescription)
                    )
                }
            }
        }
    )
}