package com.alexzh.moodtracker.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alexzh.moodtracker.core.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.core.domain.model.IconShape
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class SettingsDataSourceImpl(
    context: Context
) : SettingsDataSource {

    private val dataStore = context.settingsDataStore

    private object Keys {
        val DEFAULT_DATA_ADDED = booleanPreferencesKey("default_data_added")
        val DYNAMIC_COLORS_ENABLED = booleanPreferencesKey("dynamic_colors_enabled")
        val ICON_SHAPE = stringPreferencesKey("icon_shape")
        val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    }
    
    override suspend fun isDefaultDataAdded(): Boolean {
        return dataStore.data
            .map { preferences -> preferences[Keys.DEFAULT_DATA_ADDED] ?: false }
            .first()
    }
    
    override suspend fun setDefaultDataAdded(added: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.DEFAULT_DATA_ADDED] = added
        }
    }

    override fun isDynamicColorsEnabled(): Flow<Boolean> {
        return dataStore.data
            .map { preferences -> preferences[Keys.DYNAMIC_COLORS_ENABLED] ?: true }
    }

    override suspend fun setDynamicColorsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.DYNAMIC_COLORS_ENABLED] = enabled
        }
    }

    override fun getIconShape(default: IconShape): Flow<IconShape> {
        return dataStore.data
            .map { preferences ->
                IconShape.fromString(
                    preferences[Keys.ICON_SHAPE] ?: default.name
                )
            }
    }

    override suspend fun setIconShape(iconShape: IconShape) {
        dataStore.edit { preferences ->
            preferences[Keys.ICON_SHAPE] = iconShape.name
        }
    }

    override suspend fun getSelectedLanguage(): String? {
        return dataStore.data
            .map { preferences -> preferences[Keys.SELECTED_LANGUAGE] }
            .first()
    }

    override suspend fun setSelectedLanguage(tag: String?) {
        dataStore.edit { preferences ->
            if (tag != null) preferences[Keys.SELECTED_LANGUAGE] = tag
            else preferences.remove(Keys.SELECTED_LANGUAGE)
        }
    }
}