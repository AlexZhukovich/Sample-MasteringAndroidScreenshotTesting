package com.alexzh.moodtracker.screenshottests.dropshots

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onRoot
import com.alexzh.designsystem.component.selector.daterangeselector.DateRangeSelector
import com.alexzh.designsystem.component.selector.daterangeselector.rememberDateRangeSelectorState
import com.alexzh.designsystem.core.theme.AppTheme
import com.dropbox.dropshots.Dropshots
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class DateRangeSelectorScreenshotTest {

    companion object Companion {
        const val FILE_PATH = "design-system"
    }

    @get:Rule(order = 0)
    val dropshots = Dropshots()

    @get:Rule(order = 1)
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

        dropshots.assertSnapshot(
            bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap(),
            name = "dateRangeSelector_SelectedDayIsTheSameAsCurrentDay",
            filePath = FILE_PATH
        )
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

        dropshots.assertSnapshot(
            bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap(),
            name = "dateRangeSelector_SelectedDayIsDifferentFromCurrentDay",
            filePath = FILE_PATH
        )
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

        dropshots.assertSnapshot(
            bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap(),
            name = "dateRangeSelector_SelectedDayIsTenDaysBeforeCurrentDay",
            filePath = FILE_PATH
        )
    }
}