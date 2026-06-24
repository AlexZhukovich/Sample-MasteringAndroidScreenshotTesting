package com.alexzh.moodtracker.screenshottests.shot

import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.statistics.ActionImpactChartData
import com.alexzh.moodtracker.statistics.AverageDailyMoodChartData
import com.alexzh.moodtracker.statistics.SelectedDateRangeData
import com.alexzh.moodtracker.statistics.StatisticsScreenContent
import com.alexzh.moodtracker.statistics.StatisticsScreenUiState
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class StatisticsScreenScreenshotTest : ScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun statisticsScreen_Empty() {
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

        composeTestRule.setContent {
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

        compareScreenshot(composeTestRule.onRoot())
    }

    @Test
    fun statisticsScreen_Empty_AfterOneSecond() {
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

        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.setContent {
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

        composeTestRule.mainClock.advanceTimeBy(1000)
        compareScreenshot(composeTestRule.onRoot())
    }
}