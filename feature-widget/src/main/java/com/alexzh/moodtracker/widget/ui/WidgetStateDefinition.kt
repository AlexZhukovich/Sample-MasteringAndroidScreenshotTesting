package com.alexzh.moodtracker.widget.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.state.GlanceStateDefinition
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.widget.model.WidgetSettings
import com.alexzh.moodtracker.widget.model.WidgetTheme
import java.io.File

object WidgetStateDefinition : GlanceStateDefinition<Preferences> {

    private const val DATA_STORE_FILENAME_PREFIX = "widget_state_"
    val TRANSPARENCY_PREFERENCE_KEY = floatPreferencesKey("transparency")
    val ICON_SHAPE_PREFERENCE_KEY = stringPreferencesKey("icon_shape")
    val THEME_PREFERENCE_KEY = stringPreferencesKey("theme")

    private val dataStoreCache = mutableMapOf<String, DataStore<Preferences>>()

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<Preferences> {
        return synchronized(dataStoreCache) {
            dataStoreCache.getOrPut(fileKey) {
                PreferenceDataStoreFactory.create {
                    getLocation(context, fileKey)
                }
            }
        }
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return File(context.filesDir, "datastore/$DATA_STORE_FILENAME_PREFIX$fileKey.preferences_pb")
    }
}

fun Preferences.toWidgetSettings(): WidgetSettings {
    val transparency = this[WidgetStateDefinition.TRANSPARENCY_PREFERENCE_KEY] ?: 0f
    val iconShape = IconShape.valueOf(this[WidgetStateDefinition.ICON_SHAPE_PREFERENCE_KEY] ?: IconShape.CIRCLE.name)
    val theme = WidgetTheme.valueOf(this[WidgetStateDefinition.THEME_PREFERENCE_KEY] ?: WidgetTheme.LIGHT.name)

    return WidgetSettings(
        transparency = transparency,
        iconShape = iconShape,
        theme = theme
    )
}