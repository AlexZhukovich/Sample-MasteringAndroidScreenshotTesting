package com.alexzh.moodtracker.ui.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@Serializable
object SettingsScreenDestination

fun NavGraphBuilder.settingsScreen(
    onNavigateToManageActions: () -> Unit,
    onNavigateToThirdPartyLicenses: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToStatistics: () -> Unit
) {
    composable<SettingsScreenDestination> {
        SettingsScreen(
            viewModel = koinInject(),
            onNavigateToManageActions = onNavigateToManageActions,
            onNavigateToThirdPartyLicenses = onNavigateToThirdPartyLicenses,
            onNavigateToHome = onNavigateToHome,
            onNavigateToStatistics = onNavigateToStatistics
        )
    }
}

fun NavController.navigateToSettings(
    popUpToDestination: Any
) {
    navigate(SettingsScreenDestination) {
        popUpTo(popUpToDestination) {
            inclusive = false
        }
        launchSingleTop = true
    }
}