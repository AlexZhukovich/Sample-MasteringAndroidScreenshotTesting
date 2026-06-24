package com.alexzh.moodtracker.screenshottests.composepreview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.statistics.ActionImpactChartData
import com.alexzh.moodtracker.statistics.AverageDailyMoodChartData
import com.alexzh.moodtracker.statistics.SelectedDateRangeData
import com.alexzh.moodtracker.statistics.StatisticsScreenContent
import com.alexzh.moodtracker.statistics.StatisticsScreenUiState
import com.android.tools.screenshot.PreviewTest
import java.time.LocalDate

class StatisticsScreenScreenshotTest {

    @PreviewTest
    @Preview
    @Composable
    fun StatisticsScreen_Empty() {
        val uiState = StatisticsScreenUiState(
            isLoading = false,
            selectedDateRange = SelectedDateRangeData(
                startDate = LocalDate.of(2025, 8, 1),
                endDate = LocalDate.of(2025, 8, 31)
            ),
            averageDailyMoodChartData = AverageDailyMoodChartData(),
            actionImpactChartData = ActionImpactChartData(),
            iconShape = IconShape.CIRCLE
        )

        AppTheme {
            StatisticsScreenContent(
                uiState = uiState,
                onNavigateToHome = { },
                onPreviousMonth = { },
                onNextMonth = { },
                onNavigateToSettings = { }
            )
        }
    }
}