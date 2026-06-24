package com.alexzh.moodtracker.screenshottests.roborazzi

import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onRoot
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.home.overview.HomeScreenContent
import com.alexzh.moodtracker.home.overview.HomeScreenUiState
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
 * Screenshot tests for Home screen.
 *
 * Configuration notes:
 * - @Config(sdk = [33]) specifies API level; hardware rendering requires API 29+ for shadow support
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel4, sdk = [33])
class HomeScreenScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_Loading() {
        val uiState = HomeScreenUiState(
            isLoading = true,
            selectedDate = LocalDate.of(2025, 1, 1),
            currentDate = LocalDate.of(2025, 1, 5),
            iconShape = IconShape.CIRCLE
        )

        composeTestRule.setContent {
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

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_Empty() {
        val uiState = HomeScreenUiState(
            isLoading = false,
            selectedDate = LocalDate.of(2025, 1, 1),
            currentDate = LocalDate.of(2025, 1, 5)
        )

        composeTestRule.setContent {
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

        composeTestRule.onRoot().captureRoboImage()
    }

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

        composeTestRule.setContent {
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

        composeTestRule.onRoot().captureRoboImage()
    }

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

        composeTestRule.setContent {
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

        composeTestRule.onRoot().captureRoboImage()
    }
}