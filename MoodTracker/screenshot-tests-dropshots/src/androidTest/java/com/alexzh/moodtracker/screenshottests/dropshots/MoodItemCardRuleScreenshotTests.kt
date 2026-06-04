package com.alexzh.moodtracker.screenshottests.dropshots

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.home.overview.components.MoodItemCard
import com.dropbox.dropshots.Dropshots
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class MoodItemCardRuleScreenshotTests {
    companion object Companion {
        const val FILE_PATH = "feature-home"
    }

    @get:Rule(order = 1)
    val dropshots = Dropshots()

    @get:Rule(order = 2)
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

        dropshots.assertSnapshot(
            bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap(),
            name = "moodItemCard_SingleAction_HasShortNote_CircleIcon",
            filePath = FILE_PATH
        )
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
                            ActionItem(id = 2L, name = "Commute")
                        )
                    ),
                    iconShape = IconShape.CIRCLE,
                    onClick = {}
                )
            }
        }

        dropshots.assertSnapshot(
            bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap(),
            name = "moodItemCard_MultipleActions_WithoutNote_CircleIcon",
            filePath = FILE_PATH
        )
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

        dropshots.assertSnapshot(
            bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap(),
            name = "moodItemCard_WithoutActions_HasShortNote_CircleIcon",
            filePath = FILE_PATH
        )
    }
}