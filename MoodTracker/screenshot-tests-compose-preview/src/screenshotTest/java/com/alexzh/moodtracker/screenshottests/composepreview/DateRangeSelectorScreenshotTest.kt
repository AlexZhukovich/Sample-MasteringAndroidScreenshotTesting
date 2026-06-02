package com.alexzh.moodtracker.screenshottests.composepreview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexzh.designsystem.component.selector.daterangeselector.DateRangeSelector
import com.alexzh.designsystem.component.selector.daterangeselector.rememberDateRangeSelectorState
import com.alexzh.designsystem.core.theme.AppTheme
import com.android.tools.screenshot.PreviewTest
import java.time.LocalDate

class DateRangeSelectorScreenshotTest {

    @PreviewTest
    @Preview(showBackground = true)
    @Composable
    fun DateRangeSelector_SelectedDayIsTheSameAsCurrentDay() {
        val date = LocalDate.of(2025, 1, 15)
        val state = rememberDateRangeSelectorState(
            currentDate = date,
            selectedDate = date,
            daysCount = 7
        )

        AppTheme {
            DateRangeSelector(state = state)
        }
    }

    @PreviewTest
    @Preview(showBackground = true)
    @Composable
    fun DateRangeSelector_SelectedDayIsDifferentFromCurrentDay() {
        val date = LocalDate.of(2025, 1, 15)
        val state = rememberDateRangeSelectorState(
            currentDate = date,
            selectedDate = date.minusDays(2),
            daysCount = 7
        )

        AppTheme {
            DateRangeSelector(state = state)
        }
    }

    @PreviewTest
    @Preview(showBackground = true)
    @Composable
    fun DateRangeSelector_SelectedDayIsTenDaysBeforeCurrentDay() {
        val date = LocalDate.of(2025, 1, 15)
        val state = rememberDateRangeSelectorState(
            currentDate = date,
            selectedDate = date.minusDays(10),
            daysCount = 7
        )

        AppTheme {
            DateRangeSelector(state = state)
        }
    }
}