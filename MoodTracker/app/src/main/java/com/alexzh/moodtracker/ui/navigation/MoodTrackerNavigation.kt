package com.alexzh.moodtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alexzh.moodtracker.ui.feature.actioncategories.actionCategoriesScreen
import com.alexzh.moodtracker.ui.feature.actioncategories.ActionCategoriesScreenDestination
import com.alexzh.moodtracker.ui.feature.actioncategorydetails.ActionCategoryDetailsDestination
import com.alexzh.moodtracker.ui.feature.actioncategorydetails.actionCategoryDetailsScreen
import com.alexzh.moodtracker.ui.feature.editmood.EditMoodScreenDestination
import com.alexzh.moodtracker.ui.feature.editmood.editMoodScreen
import com.alexzh.moodtracker.ui.feature.home.HomeScreenDestination
import com.alexzh.moodtracker.ui.feature.home.homeScreen
import com.alexzh.moodtracker.ui.feature.home.navigateToHome
import com.alexzh.moodtracker.ui.feature.previewmood.PreviewMoodScreenDestination
import com.alexzh.moodtracker.ui.feature.previewmood.previewMoodScreen
import com.alexzh.moodtracker.ui.feature.settings.SettingsScreenDestination
import com.alexzh.moodtracker.ui.feature.settings.settingsScreen
import com.alexzh.moodtracker.ui.feature.statistics.navigateToStatistics
import com.alexzh.moodtracker.ui.feature.statistics.statisticsScreen

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
            onNavigateToSettings = { navController.navigate(SettingsScreenDestination) },
            onNavigateToAddMood = { navController.navigate(EditMoodScreenDestination()) },
            onNavigateToStatistics = { navController.navigateToStatistics(HomeScreenDestination) }
        )
        editMoodScreen(
            onNavigateToActionCategories = { navController.navigate(ActionCategoriesScreenDestination) },
            onNavigateUp = { navController.navigateUp() }
        )
        previewMoodScreen(
            onNavigateToEditMood = { moodId -> navController.navigate(EditMoodScreenDestination(moodId)) },
            onNavigateUp = { navController.navigateUp() }
        )
        statisticsScreen(
            onNavigateToHome = { navController.navigateToHome() }
        )
        actionCategoriesScreen(
            onNavigateToEditActionCategory = { categoryId -> navController.navigate(ActionCategoryDetailsDestination(categoryId)) },
            onNavigateUp = { navController.navigateUp() }
        )
        actionCategoryDetailsScreen(
            onNavigateUp = { navController.navigateUp() }
        )
        settingsScreen(
            onNavigateUp = { navController.navigateUp() }
        )
    }
}