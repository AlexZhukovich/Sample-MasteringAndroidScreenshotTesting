package com.alexzh.moodtracker.screenshottests.roborazzi

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.home.overview.HomeScreen
import com.alexzh.moodtracker.home.overview.HomeScreenContent
import com.alexzh.moodtracker.home.overview.HomeScreenUiState
import com.alexzh.moodtracker.home.overview.HomeScreenViewModel
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.time.LocalDate


@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel4, sdk = [33])
class HomeScreenStatelessVsStatefulScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_HasMoodItem_stateless() {
        val uiState = HomeScreenUiState(
            isLoading = false,
            moodItems = listOf(
                MoodItem(
                    id = 1,
                    mood = LocalizedMood.HAPPY,
                    date = LocalDate.of(2025, 1, 5).atTime(19, 0),
                    note = "Had a great day at work!",
                    actions = listOf(ActionItem(1, "Work"))
                ),
            ),
            selectedDate = LocalDate.of(2025, 1, 1),
            currentDate = LocalDate.of(2025, 1, 5),
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
    fun homeScreen_HasMoodItem_stateful() {
        val uiState = HomeScreenUiState(
            isLoading = false,
            moodItems = listOf(
                MoodItem(
                    id = 1,
                    mood = LocalizedMood.HAPPY,
                    date = LocalDate.of(2025, 1, 5).atTime(19, 0),
                    note = "Had a great day at work!",
                    actions = listOf(ActionItem(1, "Work"))
                ),
            ),
            selectedDate = LocalDate.of(2025, 1, 1),
            currentDate = LocalDate.of(2025, 1, 5),
        )
        val viewModel = mockk<HomeScreenViewModel>(relaxed = true)
        every { viewModel.uiState } returns MutableStateFlow(uiState)

        composeTestRule.setContent {
            AppTheme {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToEditMood = { },
                    onNavigateToAddMood = { },
                    onNavigateToStatistics = { },
                    onNavigateToSettings = { }
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage()
    }
}