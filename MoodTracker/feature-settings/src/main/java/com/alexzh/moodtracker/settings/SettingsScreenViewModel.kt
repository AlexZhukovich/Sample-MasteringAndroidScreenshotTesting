package com.alexzh.moodtracker.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.core.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.core.domain.provider.AppInfoProvider
import com.alexzh.moodtracker.common.ui.model.LocalizedIconShape
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val settingsDataSource: SettingsDataSource,
    private val appInfoProvider: AppInfoProvider
): ViewModel() {

    val uiState: StateFlow<SettingsScreenUiState> = combine(
        settingsDataSource.isDynamicColorsEnabled(),
        settingsDataSource.getIconShape()
    ) { isDynamicColorsEnabled, iconShape ->
        val localizedIconShape = when (iconShape) {
            IconShape.CIRCLE -> LocalizedIconShape.CIRCLE
            IconShape.ROUNDED_SQUARE -> LocalizedIconShape.ROUNDED_SQUARE
        }
        SettingsScreenUiState(
            isLoading = false,
            isDynamicColorsEnabled = isDynamicColorsEnabled,
            iconShape = localizedIconShape,
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
            is SettingsScreenEvent.OnIconShapeChanged -> setIconShape(event.iconShape)
        }
    }

    private fun setDynamicColorsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataSource.setDynamicColorsEnabled(enabled)
        }
    }

    private fun setIconShape(localizedIconShape: LocalizedIconShape) {
        viewModelScope.launch {
            val iconShape = when (localizedIconShape) {
                LocalizedIconShape.CIRCLE -> IconShape.CIRCLE
                LocalizedIconShape.ROUNDED_SQUARE -> IconShape.ROUNDED_SQUARE
            }
            settingsDataSource.setIconShape(iconShape)
        }
    }
}