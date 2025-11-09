package com.alexzh.moodtracker

import android.app.Application
import com.alexzh.moodtracker.core.data.initialization.DataInitializer
import com.alexzh.moodtracker.di.appModule
import com.alexzh.moodtracker.core.di.dataModule
import com.alexzh.moodtracker.actionmanagement.actionManagementModule
import com.alexzh.moodtracker.home.homeModule
import com.alexzh.moodtracker.settings.settingsModule
import com.alexzh.moodtracker.statistics.statisticsModule
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
                homeModule,
                statisticsModule,
                actionManagementModule,
                settingsModule
            )
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            dataInitializer.initializeDefaultData()
        }
    }
}