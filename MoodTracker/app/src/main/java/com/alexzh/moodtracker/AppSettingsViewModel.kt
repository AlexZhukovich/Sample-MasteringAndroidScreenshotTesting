package com.alexzh.moodtracker

import androidx.lifecycle.ViewModel
import com.alexzh.moodtracker.core.domain.datasource.SettingsDataSource

class AppSettingsViewModel(
    settingsDataSource: SettingsDataSource
) : ViewModel() {
    
    val isDynamicColorsEnabled = settingsDataSource.isDynamicColorsEnabled()
}