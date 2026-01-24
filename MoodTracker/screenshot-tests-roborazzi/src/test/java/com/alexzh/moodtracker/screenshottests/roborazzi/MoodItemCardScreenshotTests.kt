package com.alexzh.moodtracker.screenshottests.roborazzi

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.home.overview.components.MoodItemCard
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.time.LocalDateTime

/**
 * Screenshot tests for MoodItemCard component.
 *
 * Configuration notes:
 * - MoodItemCard uses ElevatedCard which displays Material3 elevation shadows
 * - @Config(sdk = [33]) specifies API level; hardware rendering requires API 29+ for shadow support
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel4, sdk = [33])
class MoodItemCardScreenshotTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun moodItemCard_SingleAction_HasShortNote_CircleIcon() {
        composeTestRule.setContent {
            AppTheme {
                MoodItemCard(
                    moodItem = MoodItem(
                        id = 1L,
                        mood = LocalizedMood.GOOD,
                        date = LocalDateTime.of(2025, 1, 2, 19, 0),
                        note = "I had a productive day",
                        actions = listOf(
                            ActionItem(id = 1L, name = "Work")
                        )
                    ),
                    iconShape = IconShape.CIRCLE,
                    onClick = {}
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun moodItemCard_MultipleActions_WithoutNote_CircleIcon() {
        composeTestRule.setContent {
            AppTheme {
                MoodItemCard(
                    moodItem = MoodItem(
                        id = 2L,
                        mood = LocalizedMood.OK,
                        date = LocalDateTime.of(2025, 1, 2, 10, 0),
                        note = "",
                        actions = listOf(
                            ActionItem(id = 1L, name = "Work"),
                            ActionItem(id = 2L, name = "Commute"),
                        )
                    ),
                    iconShape = IconShape.CIRCLE,
                    onClick = {}
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun moodItemCard_WithoutActions_HasShortNote_CircleIcon() {
        composeTestRule.setContent {
            AppTheme {
                MoodItemCard(
                    moodItem = MoodItem(
                        id = 3L,
                        mood = LocalizedMood.OK,
                        date = LocalDateTime.of(2025, 1, 2, 7, 30),
                        note = "I had difficulty getting up from bed",
                        actions = emptyList()
                    ),
                    iconShape = IconShape.CIRCLE,
                    onClick = {}
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage()
    }
}