package com.alexzh.moodtracker.screenshottests.roborazzi

import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onRoot
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.statistics.ActionImpactChartData
import com.alexzh.moodtracker.statistics.AverageDailyMoodChartData
import com.alexzh.moodtracker.statistics.SelectedDateRangeData
import com.alexzh.moodtracker.statistics.StatisticsScreenContent
import com.alexzh.moodtracker.statistics.StatisticsScreenUiState
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.time.LocalDate
import kotlin.intArrayOf

/**
 * Screenshot tests for Statistics screen.
 *
 * Configuration notes:
 * - @Config(sdk = [33]) specifies API level; hardware rendering requires API 29+ for shadow support
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel4, sdk = [33])
class StatisticsScreenScreenshotTest {
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

        composeTestRule.onRoot().captureRoboImage()
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
        composeTestRule.onRoot().captureRoboImage()
    }
}