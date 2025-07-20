package com.alexzh.moodtracker

import android.app.Application
import com.alexzh.moodtracker.data.initialization.DataInitializer
import com.alexzh.moodtracker.di.appModule
import com.alexzh.moodtracker.di.dataModule
import com.alexzh.moodtracker.ui.feature.home.homeModule
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
                homeModule
            )
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            dataInitializer.initializeDefaultData()
        }
    }
}