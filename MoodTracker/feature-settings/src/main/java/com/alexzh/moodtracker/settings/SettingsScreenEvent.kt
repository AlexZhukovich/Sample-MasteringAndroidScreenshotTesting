package com.alexzh.moodtracker.settings

import com.alexzh.moodtracker.common.ui.model.LocalizedIconShape

sealed class SettingsScreenEvent {
    data class OnDynamicColorsChanged(val enabled: Boolean) : SettingsScreenEvent()
    data class OnIconShapeChanged(val iconShape: LocalizedIconShape) : SettingsScreenEvent()
}