package com.alexzh.moodtracker.screenshottests.androidtestify

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.statistics.ActionImpactChartData
import com.alexzh.moodtracker.statistics.AverageDailyMoodChartData
import com.alexzh.moodtracker.statistics.SelectedDateRangeData
import com.alexzh.moodtracker.statistics.StatisticsScreenContent
import com.alexzh.moodtracker.statistics.StatisticsScreenUiState
import dev.testify.ComposableScreenshotRule
import dev.testify.annotation.ScreenshotInstrumentation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")
@RunWith(AndroidJUnit4::class)
class StatisticsScreenScreenshotTest {

    @get:Rule
    val rule = ComposableScreenshotRule()

    @ScreenshotInstrumentation
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

        rule
            .setCompose {
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
            .assertSame()
    }
}