package com.alexzh.moodtracker.screenshottests.dropshots

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.statistics.ActionImpactChartData
import com.alexzh.moodtracker.statistics.AverageDailyMoodChartData
import com.alexzh.moodtracker.statistics.SelectedDateRangeData
import com.alexzh.moodtracker.statistics.StatisticsScreenContent
import com.alexzh.moodtracker.statistics.StatisticsScreenUiState
import com.dropbox.dropshots.Dropshots
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class StatisticsScreenScreenshotTest {
    companion object Companion {
        const val FILE_PATH = "feature-statistics"
    }

    @get:Rule(order = 1)
    val dropshots = Dropshots()

    @get:Rule(order = 2)
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

        dropshots.assertSnapshot(
            bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap(),
            name = "statisticsScreen_Empty",
            filePath = FILE_PATH
        )
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
        dropshots.assertSnapshot(
            bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap(),
            name = "statisticsScreen_Empty_AfterOneSecond",
            filePath = FILE_PATH
        )
    }
}