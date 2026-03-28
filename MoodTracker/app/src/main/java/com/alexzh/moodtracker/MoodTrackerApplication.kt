package com.alexzh.moodtracker

import android.app.Application
import android.appwidget.AppWidgetProviderInfo
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.collection.intSetOf
import androidx.core.os.LocaleListCompat
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.alexzh.moodtracker.actionmanagement.actionManagementModule
import com.alexzh.moodtracker.common.ui.di.actionLocalizationModule
import com.alexzh.moodtracker.core.data.initialization.DataInitializer
import com.alexzh.moodtracker.core.di.dataModule
import com.alexzh.moodtracker.core.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.di.appModule
import com.alexzh.moodtracker.home.homeModule
import com.alexzh.moodtracker.settings.settingsModule
import com.alexzh.moodtracker.statistics.statisticsModule
import com.alexzh.moodtracker.widget.ui.MoodTrackerWidgetReceiver
import com.alexzh.moodtracker.widget.di.widgetModule
import com.alexzh.designsystem.core.locale.withNativeDigits
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.Locale

class MoodTrackerApplication : Application() {

    private val dataInitializer: DataInitializer by inject()
    private val settingsDataSource: SettingsDataSource by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MoodTrackerApplication)
            modules(
                appModule,
                dataModule,
                actionLocalizationModule,
                homeModule,
                statisticsModule,
                actionManagementModule,
                settingsModule,
                widgetModule
            )
        }

        val languageTag = runBlocking { settingsDataSource.getSelectedLanguage() }
        if (languageTag != null) {
            val locale = Locale.forLanguageTag(languageTag)
            Locale.setDefault(locale.withNativeDigits())
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageTag))
        }

        CoroutineScope(Dispatchers.IO).launch {
            dataInitializer.initializeDefaultData()
            initializeWidgetPreviews()
        }
    }

    private suspend fun initializeWidgetPreviews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            GlanceAppWidgetManager(this).setWidgetPreviews(
                MoodTrackerWidgetReceiver::class,
                intSetOf(AppWidgetProviderInfo.WIDGET_CATEGORY_HOME_SCREEN)
            )
        }
    }
}