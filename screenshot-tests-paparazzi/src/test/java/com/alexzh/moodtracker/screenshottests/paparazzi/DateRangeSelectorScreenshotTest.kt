package com.alexzh.moodtracker.screenshottests.paparazzi

import android.content.pm.ShortcutInfo
import androidx.compose.material3.Surface
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexzh.designsystem.component.selector.daterangeselector.DateRangeSelector
import com.alexzh.designsystem.component.selector.daterangeselector.rememberDateRangeSelectorState
import com.alexzh.designsystem.core.theme.AppTheme
import com.android.ide.common.rendering.api.SessionParams.RenderingMode
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class DateRangeSelectorScreenshotTest {

    @get:Rule(order = 0)
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_4,
        renderingMode = RenderingMode.SHRINK
    )

    @Test
    fun dateRangeSelector_SelectedDayIsTheSameAsCurrentDay() {
        paparazzi.snapshot {
            val date = LocalDate.of(2025, 1, 15)
            val state = rememberDateRangeSelectorState(
                currentDate = date,
                selectedDate = date,
                daysCount = 7
            )

            AppTheme {
                Surface {
                    DateRangeSelector(state = state)
                }
            }
        }
    }

    @Test
    fun dateRangeSelector_SelectedDayIsDifferentFromCurrentDay() {
        paparazzi.snapshot {
            val date = LocalDate.of(2025, 1, 15)
            val state = rememberDateRangeSelectorState(
                currentDate = date,
                selectedDate = date.minusDays(2),
                daysCount = 7
            )

            AppTheme {
                Surface {
                    DateRangeSelector(state = state)
                }
            }
        }
    }

    @Test
    fun dateRangeSelector_SelectedDayIsTenDaysBeforeCurrentDay() {
        paparazzi.snapshot {
            val date = LocalDate.of(2025, 1, 15)
            val state = rememberDateRangeSelectorState(
                currentDate = date,
                selectedDate = date.minusDays(10),
                daysCount = 7
            )

            AppTheme {
                Surface {
                    DateRangeSelector(state = state)
                }
            }
        }
    }
}