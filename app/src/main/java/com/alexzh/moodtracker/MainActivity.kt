package com.alexzh.moodtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.alexzh.designsystem.core.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
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