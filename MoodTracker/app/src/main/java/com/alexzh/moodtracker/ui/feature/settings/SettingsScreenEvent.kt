package com.alexzh.moodtracker.ui.feature.settings

sealed class SettingsScreenEvent {
    data class OnDynamicColorsChanged(val enabled: Boolean) : SettingsScreenEvent()
}