package com.alexzh.moodtracker.screenshottests.paparazzi

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.home.overview.components.MoodItemCard
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class MoodItemCardScreenshotTests {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_4,
        renderingMode = SessionParams.RenderingMode.SHRINK
    )

    @Test
    fun moodItemCard_SingleAction_HasShortNote_CircleIcon() {
        paparazzi.snapshot {
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
    }

    @Test
    fun moodItemCard_MultipleActions_WithoutNote_CircleIcon() {
        paparazzi.snapshot {
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
    }

    @Test
    fun moodItemCard_WithoutActions_HasShortNote_CircleIcon() {
        paparazzi.snapshot {
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
    }
}