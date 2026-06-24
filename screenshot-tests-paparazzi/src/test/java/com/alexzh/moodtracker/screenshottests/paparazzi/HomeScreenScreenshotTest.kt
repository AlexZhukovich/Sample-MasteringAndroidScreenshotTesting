package com.alexzh.moodtracker.screenshottests.paparazzi

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.home.overview.HomeScreenContent
import com.alexzh.moodtracker.home.overview.HomeScreenUiState
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class HomeScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_4
    )

    @Test
    fun homeScreen_Loading() {
        paparazzi.snapshot {
            val uiState = HomeScreenUiState(
                isLoading = true,
                selectedDate = LocalDate.of(2025, 1, 1),
                currentDate = LocalDate.of(2025, 1, 5),
                iconShape = IconShape.CIRCLE
            )

            AppTheme {
                HomeScreenContent(
                    uiState = uiState,
                    onNavigateToEditMood = { },
                    onNavigateToAddMood = { },
                    onChangeSelectedDate = { },
                    onSelectMoodItem = { },
                    onClearSelection = { },
                    onDeleteMood = { },
                    onNavigateToStatistics = { },
                    onNavigateToSettings = { }
                )
            }
        }
    }

    @Test
    fun homeScreen_Empty() {
        paparazzi.snapshot {
            val uiState = HomeScreenUiState(
                isLoading = false,
                selectedDate = LocalDate.of(2025, 1, 1),
                currentDate = LocalDate.of(2025, 1, 5)
            )

            AppTheme {
                HomeScreenContent(
                    uiState = uiState,
                    onNavigateToEditMood = { },
                    onNavigateToAddMood = { },
                    onChangeSelectedDate = { },
                    onSelectMoodItem = { },
                    onClearSelection = { },
                    onDeleteMood = { },
                    onNavigateToStatistics = { },
                    onNavigateToSettings = { }
                )
            }
        }
    }

    @Test
    fun homeScreen_HasMultipleMoodItems_CircleIcon() {
        paparazzi.snapshot {
            val uiState = HomeScreenUiState(
                isLoading = false,
                moodItems = listOf(
                    MoodItem(
                        id = 1,
                        mood = LocalizedMood.HAPPY,
                        date = LocalDate.of(2025, 1, 5).atTime(19, 0),
                        note = "Had a great day at work!",
                        actions = listOf(
                            ActionItem(1, "Running"),
                            ActionItem(2, "Meditating"),
                            ActionItem(3, "Reading")
                        )
                    ),
                    MoodItem(
                        id = 2,
                        mood = LocalizedMood.OK,
                        date = LocalDate.of(2025, 1, 5).atTime(15, 45),
                        note = "",
                        actions = emptyList()
                    ),
                ),
                selectedDate = LocalDate.of(2025, 1, 1),
                currentDate = LocalDate.of(2025, 1, 5),
                iconShape = IconShape.CIRCLE
            )

            AppTheme {
                HomeScreenContent(
                    uiState = uiState,
                    onNavigateToEditMood = { },
                    onNavigateToAddMood = { },
                    onChangeSelectedDate = { },
                    onSelectMoodItem = { },
                    onClearSelection = { },
                    onDeleteMood = { },
                    onNavigateToStatistics = { },
                    onNavigateToSettings = { }
                )
            }
        }
    }

    @Test
    fun homeScreen_HasMultipleMoodItems_RoundedSquareIcon() {
        paparazzi.snapshot {
            val uiState = HomeScreenUiState(
                isLoading = false,
                moodItems = listOf(
                    MoodItem(
                        id = 1,
                        mood = LocalizedMood.HAPPY,
                        date = LocalDate.of(2025, 1, 5).atTime(19, 0),
                        note = "Had a great day at work!",
                        actions = listOf(
                            ActionItem(1, "Running"),
                            ActionItem(2, "Meditating"),
                            ActionItem(3, "Reading")
                        )
                    ),
                    MoodItem(
                        id = 2,
                        mood = LocalizedMood.OK,
                        date = LocalDate.of(2025, 1, 5).atTime(15, 45),
                        note = "",
                        actions = emptyList()
                    ),
                ),
                selectedDate = LocalDate.of(2025, 1, 1),
                currentDate = LocalDate.of(2025, 1, 5),
                iconShape = IconShape.ROUNDED_SQUARE
            )

            AppTheme {
                HomeScreenContent(
                    uiState = uiState,
                    onNavigateToEditMood = { },
                    onNavigateToAddMood = { },
                    onChangeSelectedDate = { },
                    onSelectMoodItem = { },
                    onClearSelection = { },
                    onDeleteMood = { },
                    onNavigateToStatistics = { },
                    onNavigateToSettings = { }
                )
            }
        }
    }
}