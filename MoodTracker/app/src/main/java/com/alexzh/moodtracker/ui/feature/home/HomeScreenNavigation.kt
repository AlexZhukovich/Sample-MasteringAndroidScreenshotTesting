package com.alexzh.moodtracker.ui.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object HomeScreenDestination

fun NavGraphBuilder.homeScreen(
    onNavigateToEditMood: (moodId: Long) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAddMood: () -> Unit,
    onNavigateToStatistics: () -> Unit
) {
    composable<HomeScreenDestination> {
        HomeScreen(
            viewModel = koinViewModel<HomeScreenViewModel>(),
            onNavigateToEditMood = onNavigateToEditMood,
            onNavigateToSettings = onNavigateToSettings,
            onNavigateToStatistics = onNavigateToStatistics,
            onNavigateToAddMood = onNavigateToAddMood
        )
    }
}

fun NavController.navigateToHome() {
    navigate(HomeScreenDestination) {
        popUpTo(0) {
            inclusive = false
        }
        launchSingleTop = true
    }
}