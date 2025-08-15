package com.alexzh.moodtracker.ui.feature.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@Serializable
object SettingsScreenDestination

fun NavGraphBuilder.settingsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToManageActions: () -> Unit,
    onNavigateToThirdPartyLicenses: () -> Unit
) {
    composable<SettingsScreenDestination> {
        SettingsScreen(
            viewModel = koinInject(),
            onNavigateUp = onNavigateUp,
            onNavigateToManageActions = onNavigateToManageActions,
            onNavigateToThirdPartyLicenses = onNavigateToThirdPartyLicenses
        )
    }
}