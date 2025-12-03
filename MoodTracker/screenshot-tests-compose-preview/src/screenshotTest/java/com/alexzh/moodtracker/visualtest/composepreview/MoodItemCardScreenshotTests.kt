package com.alexzh.moodtracker.visualtest.composepreview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.home.overview.components.MoodItemCard
import com.android.tools.screenshot.PreviewTest
import java.time.LocalDateTime

class MoodItemCardScreenshotTests {

    @PreviewTest
    @Preview(showBackground = true)
    @Composable
    fun MoodItemCard_SingleAction_HasShortNote_CircleIcon() {
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

    @PreviewTest
    @Preview(showBackground = true)
    @Composable
    fun MoodItemCard_MultipleActions_WithoutNote_CircleIcon() {
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

    @PreviewTest
    @Preview(showBackground = true)
    @Composable
    fun MoodItemCard_WithoutActions_HasShortNote_CircleIcon() {
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