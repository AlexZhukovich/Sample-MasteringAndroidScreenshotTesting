package com.alexzh.moodtracker.widget.model

import com.alexzh.moodtracker.widget.R

enum class LocalizedWidgetTheme(
    val label: Int
) {
    LIGHT(R.string.widgetConfigurationScreen_theme_light_label),
    DARK(R.string.widgetConfigurationScreen_theme_dark_label);

    fun toWidgetTheme(): WidgetTheme {
        return when (this) {
            LIGHT -> WidgetTheme.LIGHT
            DARK -> WidgetTheme.DARK
        }
    }

    companion object {
        fun fromWidgetTheme(theme: WidgetTheme): LocalizedWidgetTheme {
            return when (theme) {
                WidgetTheme.LIGHT -> LIGHT
                WidgetTheme.DARK -> DARK
            }
        }
    }
}