package com.alexzh.moodtracker.widget.ui.configuration

import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.widget.model.WidgetTheme

sealed class WidgetConfigurationEvent {
    data class OnTransparencyChanged(val transparency: Float) : WidgetConfigurationEvent()
    data class OnIconShapeChanged(val iconShape: IconShape) : WidgetConfigurationEvent()
    data class OnThemeChanged(val theme: WidgetTheme) : WidgetConfigurationEvent()
    data object OnApply : WidgetConfigurationEvent()
    data object OnCancel : WidgetConfigurationEvent()
}