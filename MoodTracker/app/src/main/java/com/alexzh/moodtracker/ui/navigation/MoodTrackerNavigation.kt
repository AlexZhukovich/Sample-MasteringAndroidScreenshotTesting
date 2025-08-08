package com.alexzh.moodtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alexzh.moodtracker.ui.feature.editmood.EditMoodScreenDestination
import com.alexzh.moodtracker.ui.feature.editmood.editMoodScreen
import com.alexzh.moodtracker.ui.feature.home.HomeScreenDestination
import com.alexzh.moodtracker.ui.feature.home.homeScreen
import com.alexzh.moodtracker.ui.feature.previewmood.PreviewMoodScreenDestination
import com.alexzh.moodtracker.ui.feature.previewmood.previewMoodScreen

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
            onNavigateToMoodPreview = { moodId -> navController.navigate(PreviewMoodScreenDestination(moodId)) },
            onNavigateToAddMood = { navController.navigate(EditMoodScreenDestination()) }
        )
        editMoodScreen(
            onNavigateUp = { navController.navigateUp() }
        )
        previewMoodScreen(
            onNavigateToEditMood = { moodId -> navController.navigate(EditMoodScreenDestination(moodId)) },
            onNavigateUp = { navController.navigateUp() }
        )
    }
}