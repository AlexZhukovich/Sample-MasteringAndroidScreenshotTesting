package com.alexzh.moodtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.feature.actioncategories.ActionCategoriesScreenDestination
import com.alexzh.moodtracker.ui.feature.actioncategories.actionCategoriesScreen
import com.alexzh.moodtracker.ui.feature.editmood.EditMoodScreenDestination
import com.alexzh.moodtracker.ui.feature.editmood.editMoodScreen
import com.alexzh.moodtracker.ui.feature.home.HomeScreenDestination
import com.alexzh.moodtracker.ui.feature.home.homeScreen
import com.alexzh.moodtracker.ui.feature.home.navigateToHome
import com.alexzh.moodtracker.ui.feature.settings.licence.SettingsLicensesScreenDestination
import com.alexzh.moodtracker.ui.feature.settings.licence.settingsLicensesScreen
import com.alexzh.moodtracker.ui.feature.settings.navigateToSettings
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
            onNavigateToEditMood = { moodId -> navController.navigate(EditMoodScreenDestination(moodId)) },
            onNavigateToAddMood = { navController.navigate(EditMoodScreenDestination()) },
            onNavigateToStatistics = { navController.navigateToStatistics(HomeScreenDestination) },
            onNavigateToSettings = { navController.navigateToSettings(HomeScreenDestination) },
        )
        editMoodScreen(
            onNavigateToActionCategories = { navController.navigate(ActionCategoriesScreenDestination) },
            onNavigateUp = { navController.navigateUp() }
        )
        statisticsScreen(
            onNavigateToHome = { navController.navigateToHome() },
            onNavigateToSettings = { navController.navigateToSettings(HomeScreenDestination) }
        )
        actionCategoriesScreen(
            onNavigateUp = { navController.navigateUp() }
        )
        settingsScreen(
            onNavigateToManageActions = { navController.navigate(ActionCategoriesScreenDestination) },
            onNavigateToThirdPartyLicenses = { navController.navigate(SettingsLicensesScreenDestination) },
            onNavigateToHome = { navController.navigateToHome() },
            onNavigateToStatistics = { navController.navigateToStatistics(HomeScreenDestination) }
        )
        settingsLicensesScreen(
            librariesResourceId = R.raw.aboutlibraries,
            onNavigateUp = { navController.navigateUp() }
        )
    }
}