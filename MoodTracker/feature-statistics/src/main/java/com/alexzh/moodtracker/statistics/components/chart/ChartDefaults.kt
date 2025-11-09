package com.alexzh.moodtracker.statistics.components.chart

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.common.ui.model.LocalizedMood

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
    internal fun defaultHappinessIconProvider(iconShape: IconShape): @Composable (Float) -> Painter? = { happiness ->
        when (happiness) {
            5.0f -> painterResource(LocalizedMood.HAPPY.getIcon(iconShape))
            4.0f -> painterResource(LocalizedMood.GOOD.getIcon(iconShape))
            3.0f -> painterResource(LocalizedMood.OK.getIcon(iconShape))
            2.0f -> painterResource(LocalizedMood.SAD.getIcon(iconShape))
            1.0f -> painterResource(LocalizedMood.ANGRY.getIcon(iconShape))
            else -> null
        }
    }
}