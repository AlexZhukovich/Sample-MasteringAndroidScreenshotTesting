package com.alexzh.moodtracker.ui.feature.settings

data class SettingsScreenUiState(
    val isLoading: Boolean = false,
    val isDynamicColorsEnabled: Boolean = true
)