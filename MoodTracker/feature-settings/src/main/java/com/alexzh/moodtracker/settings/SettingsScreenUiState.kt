package com.alexzh.moodtracker.settings

import com.alexzh.moodtracker.common.ui.model.LocalizedIconShape
import com.alexzh.moodtracker.common.ui.model.LocalizedLanguage

data class SettingsScreenUiState(
    val isLoading: Boolean = false,
    val isDynamicColorsEnabled: Boolean = true,
    val iconShape: LocalizedIconShape = LocalizedIconShape.CIRCLE,
    val language: LocalizedLanguage = LocalizedLanguage.SYSTEM_DEFAULT,
    val appVersion: String = ""
)