package com.alexzh.moodtracker.screenshottests.androidtestify

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexzh.designsystem.component.selector.daterangeselector.DateRangeSelector
import com.alexzh.designsystem.component.selector.daterangeselector.rememberDateRangeSelectorState
import com.alexzh.designsystem.core.theme.AppTheme
import dev.testify.ComposableScreenshotRule
import dev.testify.annotation.ScreenshotInstrumentation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")
@RunWith(AndroidJUnit4::class)
class DateRangeSelectorScreenshotTest {

    @get:Rule
    val rule = ComposableScreenshotRule()

    @ScreenshotInstrumentation
    @Test
    fun dateRangeSelector_SelectedDayIsTheSameAsCurrentDay() {
        rule
            .setCompose {
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
            .assertSame()
    }

    @ScreenshotInstrumentation
    @Test
    fun dateRangeSelector_SelectedDayIsDifferentFromCurrentDay() {
        rule
            .setCompose {
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
            .assertSame()
    }

    @ScreenshotInstrumentation
    @Test
    fun dateRangeSelector_SelectedDayIsTenDaysBeforeCurrentDay() {
        rule
            .setCompose {
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
            .assertSame()
    }
}