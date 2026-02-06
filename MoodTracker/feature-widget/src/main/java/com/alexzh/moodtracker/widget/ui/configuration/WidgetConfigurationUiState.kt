package com.alexzh.moodtracker.widget.ui.configuration

import com.alexzh.moodtracker.common.ui.model.LocalizedIconShape
import com.alexzh.moodtracker.widget.model.WidgetTheme

data class WidgetConfigurationUiState(
    val transparency: Float = 0f,
    val iconShape: LocalizedIconShape = LocalizedIconShape.CIRCLE,
    val theme: WidgetTheme = WidgetTheme.LIGHT
)