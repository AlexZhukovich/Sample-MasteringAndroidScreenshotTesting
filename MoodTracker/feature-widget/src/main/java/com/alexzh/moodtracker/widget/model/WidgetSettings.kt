package com.alexzh.moodtracker.widget.model

import com.alexzh.moodtracker.core.domain.model.IconShape

data class WidgetSettings(
    val transparency: Float = 0f,
    val iconShape: IconShape = IconShape.CIRCLE,
    val theme: WidgetTheme = WidgetTheme.LIGHT
)