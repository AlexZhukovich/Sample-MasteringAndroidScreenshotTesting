package com.alexzh.moodtracker.ui.feature.settings

import com.alexzh.moodtracker.ui.model.LocalizedIconShape

sealed class SettingsScreenEvent {
    data class OnDynamicColorsChanged(val enabled: Boolean) : SettingsScreenEvent()
    data class OnIconShapeChanged(val iconShape: LocalizedIconShape) : SettingsScreenEvent()
}