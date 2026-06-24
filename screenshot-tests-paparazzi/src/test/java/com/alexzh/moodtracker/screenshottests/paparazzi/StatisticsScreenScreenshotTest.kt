package com.alexzh.moodtracker.screenshottests.paparazzi

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.statistics.ActionImpactChartData
import com.alexzh.moodtracker.statistics.AverageDailyMoodChartData
import com.alexzh.moodtracker.statistics.SelectedDateRangeData
import com.alexzh.moodtracker.statistics.StatisticsScreenContent
import com.alexzh.moodtracker.statistics.StatisticsScreenUiState
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class StatisticsScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_4
    )

    @Test
    fun statisticsScreen_Empty() {
        paparazzi.snapshot {
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
}