package com.alexzh.moodtracker.ui.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.domain.provider.AppInfoProvider
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val settingsDataSource: SettingsDataSource,
    private val appInfoProvider: AppInfoProvider
): ViewModel() {

    val uiState: StateFlow<SettingsScreenUiState> = settingsDataSource.isDynamicColorsEnabled()
        .map { isDynamicColorsEnabled ->
            SettingsScreenUiState(
                isLoading = false,
                isDynamicColorsEnabled = isDynamicColorsEnabled,
                appVersion = appInfoProvider.getAppInfo().versionName
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = SettingsScreenUiState(isLoading = true)
        )

    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.OnDynamicColorsChanged -> setDynamicColorsEnabled(event.enabled)
        }
    }

    private fun setDynamicColorsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataSource.setDynamicColorsEnabled(enabled)
        }
    }
}