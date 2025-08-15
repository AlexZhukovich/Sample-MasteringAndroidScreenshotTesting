package com.alexzh.moodtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.alexzh.moodtracker.ui.navigation.MoodTrackerNavigation
import com.alexzh.moodtracker.ui.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val appSettingsViewModel: AppSettingsViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDynamicColorsEnabled by appSettingsViewModel.isDynamicColorsEnabled.collectAsState(initial = true)
            
            AppTheme(
                dynamicColor = isDynamicColorsEnabled
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MoodTrackerNavigation()
                }
            }
        }
    }
}