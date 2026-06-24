package com.alexzh.moodtracker.screenshottests.androidtestify

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.home.overview.HomeScreenContent
import com.alexzh.moodtracker.home.overview.HomeScreenUiState
import dev.testify.ComposableScreenshotRule
import dev.testify.annotation.ScreenshotInstrumentation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")
@RunWith(AndroidJUnit4::class)
class HomeScreenScreenshotTest {
    @get:Rule
    val rule = ComposableScreenshotRule()

    @ScreenshotInstrumentation
    @Test
    fun homeScreen_Loading() {
        val uiState = HomeScreenUiState(
            isLoading = true,
            selectedDate = LocalDate.of(2025, 1, 1),
            currentDate = LocalDate.of(2025, 1, 5),
            iconShape = IconShape.CIRCLE
        )

        rule
            .setCompose {
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
            .assertSame()
    }

    @ScreenshotInstrumentation
    @Test
    fun homeScreen_Empty() {
        val uiState = HomeScreenUiState(
            isLoading = false,
            selectedDate = LocalDate.of(2025, 1, 1),
            currentDate = LocalDate.of(2025, 1, 5)
        )

        rule
            .setCompose {
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
            .assertSame()
    }

    @ScreenshotInstrumentation
    @Test
    fun homeScreen_HasMultipleMoodItems_CircleIcon() {
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

        rule
            .setCompose {
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
            .assertSame()
    }

    @ScreenshotInstrumentation
    @Test
    fun homeScreen_HasMultipleMoodItems_RoundedSquareIcon() {
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

        rule
            .setCompose {
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
            .assertSame()
    }
}