package com.alexzh.moodtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alexzh.moodtracker.ui.feature.home.HomeScreenDestination
import com.alexzh.moodtracker.ui.feature.home.homeScreen

@Composable
fun MoodTrackerNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = HomeScreenDestination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(
            onNavigateToMoodPreview = { },
            onNavigateToAddMood = { }
        )
    }
}