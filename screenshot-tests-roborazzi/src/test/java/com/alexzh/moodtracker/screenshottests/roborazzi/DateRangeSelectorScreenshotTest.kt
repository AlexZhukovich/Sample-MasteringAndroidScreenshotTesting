package com.alexzh.moodtracker.screenshottests.roborazzi

import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onRoot
import com.alexzh.designsystem.component.selector.daterangeselector.DateRangeSelector
import com.alexzh.designsystem.component.selector.daterangeselector.rememberDateRangeSelectorState
import com.alexzh.designsystem.core.theme.AppTheme
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
 * Screenshot tests for DateRangeSelector component.
 *
 * Configuration notes:
 * - @Config(sdk = [33]) specifies API level; hardware rendering requires API 29+ for shadow support
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel4, sdk = [33])
class DateRangeSelectorScreenshotTest {
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

        composeTestRule.onRoot().captureRoboImage()
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
        composeTestRule.onRoot().captureRoboImage()
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
        composeTestRule.onRoot().captureRoboImage()
    }
}