package com.alexzh.moodtracker.widget.ui.configuration

import com.alexzh.moodtracker.common.ui.model.LocalizedIconShape
import com.alexzh.moodtracker.widget.model.LocalizedWidgetTheme

sealed class WidgetConfigurationEvent {
    data class OnTransparencyChanged(val transparency: Float) : WidgetConfigurationEvent()
    data class OnIconShapeChanged(val iconShape: LocalizedIconShape) : WidgetConfigurationEvent()
    data class OnThemeChanged(val theme: LocalizedWidgetTheme) : WidgetConfigurationEvent()
    data object OnApply : WidgetConfigurationEvent()
    data object OnCancel : WidgetConfigurationEvent()
}