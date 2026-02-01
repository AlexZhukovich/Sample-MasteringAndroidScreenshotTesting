package com.alexzh.moodtracker.widget.ui.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.widget.model.WidgetSettings
import com.alexzh.moodtracker.widget.model.WidgetTheme
import com.alexzh.moodtracker.widget.data.WidgetSettingsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WidgetConfigurationViewModel(
    private val widgetDataSource: WidgetSettingsDataSource,
    private val appWidgetId: Int,
    private val onConfigurationComplete: (Boolean) -> Unit
) : ViewModel() {

    private val _uiState = MutableStateFlow(WidgetConfigurationUiState())
    val uiState: StateFlow<WidgetConfigurationUiState> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            val glanceId = widgetDataSource.getGlanceId(appWidgetId)
            val settings = widgetDataSource.getWidgetSettings(glanceId)
            _uiState.value = WidgetConfigurationUiState(
                transparency = settings.transparency,
                iconShape = settings.iconShape,
                theme = settings.theme
            )
        }
    }

    fun onEvent(event: WidgetConfigurationEvent) {
        when (event) {
            is WidgetConfigurationEvent.OnTransparencyChanged -> updateTransparency(event.transparency)
            is WidgetConfigurationEvent.OnIconShapeChanged -> updateIconShape(event.iconShape)
            is WidgetConfigurationEvent.OnThemeChanged -> updateTheme(event.theme)
            is WidgetConfigurationEvent.OnApply -> applyConfiguration()
            is WidgetConfigurationEvent.OnCancel -> cancelConfiguration()
        }
    }

    private fun updateTransparency(transparency: Float) {
        _uiState.update { it.copy(transparency = transparency) }
    }

    private fun updateIconShape(iconShape: IconShape) {
        _uiState.update { it.copy(iconShape = iconShape) }
    }

    private fun updateTheme(theme: WidgetTheme) {
        _uiState.update { it.copy(theme = theme) }
    }

    private fun applyConfiguration() {
        val state = _uiState.value
        val settings = WidgetSettings(
            transparency = state.transparency,
            iconShape = state.iconShape,
            theme = state.theme
        )

        runBlocking {
            val glanceId = widgetDataSource.getGlanceId(appWidgetId)
            widgetDataSource.saveWidgetSettings(glanceId, settings)
        }

        onConfigurationComplete(true)
    }

    private fun cancelConfiguration() {
        onConfigurationComplete(false)
    }
}