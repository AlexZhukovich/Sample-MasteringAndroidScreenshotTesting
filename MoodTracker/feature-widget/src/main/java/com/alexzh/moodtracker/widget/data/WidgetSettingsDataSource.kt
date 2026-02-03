package com.alexzh.moodtracker.widget.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.mutablePreferencesOf
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.appwidget.state.updateAppWidgetState
import com.alexzh.moodtracker.widget.model.WidgetSettings
import com.alexzh.moodtracker.widget.ui.WidgetStateDefinition
import com.alexzh.moodtracker.widget.ui.toWidgetSettings

class WidgetSettingsDataSource(private val context: Context) {

    fun getGlanceId(appWidgetId: Int): GlanceId =
        GlanceAppWidgetManager(context).getGlanceIdBy(appWidgetId)

    suspend fun getWidgetSettings(glanceId: GlanceId): WidgetSettings {
        val prefs: Preferences = getAppWidgetState(context, WidgetStateDefinition, glanceId)
        return prefs.toWidgetSettings()
    }

    suspend fun saveWidgetSettings(glanceId: GlanceId, settings: WidgetSettings) {
        updateAppWidgetState(context, WidgetStateDefinition, glanceId) { _: Preferences ->
            mutablePreferencesOf(
                WidgetStateDefinition.TRANSPARENCY_PREFERENCE_KEY to settings.transparency,
                WidgetStateDefinition.THEME_PREFERENCE_KEY to settings.theme.name,
                WidgetStateDefinition.ICON_SHAPE_PREFERENCE_KEY to settings.iconShape.name
            )
        }
    }
}