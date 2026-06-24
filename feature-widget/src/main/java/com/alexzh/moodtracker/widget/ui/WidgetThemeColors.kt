package com.alexzh.moodtracker.widget.ui

import androidx.glance.material3.ColorProviders
import com.alexzh.designsystem.core.theme.darkScheme
import com.alexzh.designsystem.core.theme.lightScheme
import com.alexzh.moodtracker.widget.model.WidgetTheme

private val LightGlanceColors = ColorProviders(
    light = lightScheme,
    dark = lightScheme
)

private val DarkGlanceColors = ColorProviders(
    light = darkScheme,
    dark = darkScheme
)

fun WidgetTheme.toGlanceColors() = when (this) {
    WidgetTheme.LIGHT -> LightGlanceColors
    WidgetTheme.DARK -> DarkGlanceColors
}