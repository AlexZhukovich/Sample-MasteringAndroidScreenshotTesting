package com.alexzh.moodtracker

import android.app.Application
import android.appwidget.AppWidgetProviderInfo
import android.os.Build
import androidx.collection.intSetOf
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.alexzh.moodtracker.actionmanagement.actionManagementModule
import com.alexzh.moodtracker.common.ui.di.actionLocalizationModule
import com.alexzh.moodtracker.core.data.initialization.DataInitializer
import com.alexzh.moodtracker.core.di.dataModule
import com.alexzh.moodtracker.di.appModule
import com.alexzh.moodtracker.home.homeModule
import com.alexzh.moodtracker.settings.settingsModule
import com.alexzh.moodtracker.statistics.statisticsModule
import com.alexzh.moodtracker.widget.ui.MoodTrackerWidgetReceiver
import com.alexzh.moodtracker.widget.di.widgetModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoodTrackerApplication : Application() {
    
    private val dataInitializer: DataInitializer by inject()
    
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