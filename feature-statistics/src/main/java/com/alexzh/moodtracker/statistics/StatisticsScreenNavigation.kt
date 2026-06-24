package com.alexzh.moodtracker.statistics

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@Serializable
object StatisticsScreenDestination

fun NavGraphBuilder.statisticsScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    composable<StatisticsScreenDestination> {
        StatisticsScreen(
            viewModel = koinInject(),
            onNavigateToHome = onNavigateToHome,
            onNavigateToSettings = onNavigateToSettings
        )
    }
}

fun NavController.navigateToStatistics(
    popUpToDestination: Any
) {
    navigate(StatisticsScreenDestination) {
        popUpTo(popUpToDestination) {
            inclusive = false
        }
        launchSingleTop = true
    }
}