package com.alexzh.moodtracker.ui.feature.settings

import com.alexzh.moodtracker.ui.model.LocalizedIconShape

data class SettingsScreenUiState(
    val isLoading: Boolean = false,
    val isDynamicColorsEnabled: Boolean = true,
    val iconShape: LocalizedIconShape = LocalizedIconShape.CIRCLE,
    val appVersion: String = ""
)