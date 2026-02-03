package com.alexzh.moodtracker.widget.ui.configuration

import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.widget.model.WidgetTheme

data class WidgetConfigurationUiState(
    val transparency: Float = 0f,
    val iconShape: IconShape = IconShape.CIRCLE,
    val theme: WidgetTheme = WidgetTheme.LIGHT
)