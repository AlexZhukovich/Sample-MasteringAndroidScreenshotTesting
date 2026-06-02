package com.alexzh.moodtracker.screenshottests.shot

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexzh.designsystem.component.selector.daterangeselector.DateRangeSelector
import com.alexzh.designsystem.component.selector.daterangeselector.rememberDateRangeSelectorState
import com.alexzh.designsystem.core.theme.AppTheme
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class DateRangeSelectorScreenshotTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dateRangeSelector_SelectedDayIsTheSameAsCurrentDay() {
        composeTestRule.setContent {
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
        compareScreenshot(composeTestRule.onRoot())
    }

    @Test
    fun dateRangeSelector_SelectedDayIsDifferentFromCurrentDay() {
        composeTestRule.setContent {
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
        compareScreenshot(composeTestRule.onRoot())
    }

    @Test
    fun dateRangeSelector_SelectedDayIsTenDaysBeforeCurrentDay() {
        composeTestRule.setContent {
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
        compareScreenshot(composeTestRule.onRoot())
    }
}