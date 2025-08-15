package com.alexzh.moodtracker.ui.feature.settings.licence

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SettingsLicensesScreenDestination

fun NavGraphBuilder.settingsLicensesScreen(
    onNavigateUp: () -> Unit
) {
    composable<SettingsLicensesScreenDestination> {
        SettingLicensesScreen(
            onNavigateUp = onNavigateUp
        )
    }
}