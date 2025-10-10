package com.alexzh.moodtracker.ui.designsystem.chart

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.alexzh.moodtracker.R

object ChartDefaults {

    @Composable
    fun defaultBarColorProvider(isDarkColor: Boolean = isSystemInDarkTheme()): (Float) -> Color = { value ->
        when (value) {
            0f -> ChartColors.ZERO.getColor(isDarkColor)
            in 0.01f..2.5f -> ChartColors.LOW_VALUES.getColor(isDarkColor)
            in 2.5f..3.8f -> ChartColors.MEDIUM_VALUES.getColor(isDarkColor)
            else -> ChartColors.HIGH_VALUES.getColor(isDarkColor)
        }
    }

    @Composable
    internal fun defaultHappinessIconProvider(): @Composable (Float) -> Painter? = { happiness ->
        when (happiness) {
            5.0f -> painterResource(id = R.drawable.ic_mood_happy_colorful)
            4.0f -> painterResource(id = R.drawable.ic_mood_good_colorful)
            3.0f -> painterResource(id = R.drawable.ic_mood_ok_colorful)
            2.0f -> painterResource(id = R.drawable.ic_mood_sad_colorful)
            1.0f -> painterResource(id = R.drawable.ic_mood_angry_colorful)
            else -> null
        }
    }
}