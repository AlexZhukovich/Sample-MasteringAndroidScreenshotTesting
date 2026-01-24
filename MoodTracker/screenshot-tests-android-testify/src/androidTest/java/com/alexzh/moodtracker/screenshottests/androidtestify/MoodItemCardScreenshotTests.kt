package com.alexzh.moodtracker.screenshottests.androidtestify

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.home.overview.components.MoodItemCard
import dev.testify.ComposableScreenshotRule
import dev.testify.annotation.ScreenshotInstrumentation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class MoodItemCardScreenshotTests {

    @get:Rule
    val rule = ComposableScreenshotRule()

    @ScreenshotInstrumentation
    @Test
    fun moodItemCard_SingleAction_HasShortNote_CircleIcon() {
        rule
            .setCompose {
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
            .assertSame()
    }

    @ScreenshotInstrumentation
    @Test
    fun moodItemCard_MultipleActions_WithoutNote_CircleIcon() {
        rule
            .setCompose {
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
            .assertSame()
    }

    @ScreenshotInstrumentation
    @Test
    fun moodItemCard_WithoutActions_HasShortNote_CircleIcon() {
        rule
            .setCompose {
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
            .assertSame()
    }
}