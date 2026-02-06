package com.alexzh.moodtracker.settings

import com.alexzh.moodtracker.common.ui.model.LocalizedIconShape

data class SettingsScreenUiState(
    val isLoading: Boolean = false,
    val isDynamicColorsEnabled: Boolean = true,
    val iconShape: LocalizedIconShape = LocalizedIconShape.CIRCLE,
    val appVersion: String = ""
)