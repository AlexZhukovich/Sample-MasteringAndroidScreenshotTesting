package com.alexzh.moodtracker.settings

import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.designsystem.core.locale.withNativeDigits
import com.alexzh.moodtracker.core.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.core.domain.provider.AppInfoProvider
import com.alexzh.moodtracker.common.ui.model.LocalizedIconShape
import com.alexzh.moodtracker.common.ui.model.LocalizedLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class SettingsScreenViewModel(
    private val settingsDataSource: SettingsDataSource,
    private val appInfoProvider: AppInfoProvider
): ViewModel() {

    private val _selectedLanguage = MutableStateFlow(currentLanguage())

    val uiState: StateFlow<SettingsScreenUiState> = combine(
        settingsDataSource.isDynamicColorsEnabled(),
        settingsDataSource.getIconShape(),
        _selectedLanguage
    ) { isDynamicColorsEnabled, iconShape, language ->
        SettingsScreenUiState(
            isLoading = false,
            isDynamicColorsEnabled = isDynamicColorsEnabled,
            iconShape = LocalizedIconShape.fromIconShape(iconShape),
            language = language,
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
            is SettingsScreenEvent.OnLanguageChanged -> setLanguage(event.language)
        }
    }

    private fun setDynamicColorsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataSource.setDynamicColorsEnabled(enabled)
        }
    }

    private fun setIconShape(localizedIconShape: LocalizedIconShape) {
        viewModelScope.launch {
            settingsDataSource.setIconShape(localizedIconShape.toIconShape())
        }
    }

    private fun setLanguage(language: LocalizedLanguage) {
        _selectedLanguage.value = language
        val locales = if (language == LocalizedLanguage.SYSTEM_DEFAULT) {
            val systemLocale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
                ?: Locale.getDefault()
            Locale.setDefault(systemLocale.withNativeDigits())
            LocaleListCompat.getEmptyLocaleList()
        } else {
            val newLocale = Locale.forLanguageTag(language.tag)
            Locale.setDefault(newLocale.withNativeDigits())
            LocaleListCompat.forLanguageTags(language.tag)
        }
        AppCompatDelegate.setApplicationLocales(locales)
        viewModelScope.launch {
            settingsDataSource.setSelectedLanguage(language.tag.ifEmpty { null })
        }
    }

    private fun currentLanguage(): LocalizedLanguage {
        val locales = AppCompatDelegate.getApplicationLocales()
        if (locales.isEmpty) return LocalizedLanguage.SYSTEM_DEFAULT
        val tag = locales[0]?.language ?: return LocalizedLanguage.SYSTEM_DEFAULT
        return LocalizedLanguage.fromLocaleTag(tag)
    }
}