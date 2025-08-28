package com.alexzh.moodtracker.ui.feature.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.chart.ActionToHappinessChart
import com.alexzh.moodtracker.ui.designsystem.chart.AverageDailyMoodChart
import com.alexzh.moodtracker.ui.designsystem.section.CardSection
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

@Composable
fun StatisticsScreenContent(
    uiState: StatisticsScreenUiState,
    onNavigateToHome: () -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
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
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AverageDailyMoodSection(
                    averageDailyMoodChartData = uiState.averageDailyMoodChartData
                )
                ActionToHappinessSection(
                    actionToHappinessChartData = uiState.actionToHappinessChartData
                )
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
        capitalizeTitle = true
    ) {
        AverageDailyMoodChart(
            modifier = Modifier.fillMaxWidth()
                .height(250.dp),
            data = averageDailyMoodChartData.data,
            scrollPosition = averageDailyMoodChartData.scrollPosition
        )
    }
}

@Composable
private fun ActionToHappinessSection(
    actionToHappinessChartData: ActionToHappinessChartData
) {
    CardSection(
        modifier = Modifier.fillMaxWidth(),
        title = stringResource(R.string.statisticsScreen_actionToHappinessSection_label),
        capitalizeTitle = true
    ) {
        ActionToHappinessChart(
            data = actionToHappinessChartData.data,
        )
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