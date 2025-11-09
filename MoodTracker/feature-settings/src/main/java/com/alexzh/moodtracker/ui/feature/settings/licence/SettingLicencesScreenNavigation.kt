package com.alexzh.moodtracker.ui.feature.settings.licence

import androidx.annotation.RawRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SettingsLicensesScreenDestination

fun NavGraphBuilder.settingsLicensesScreen(
    @RawRes librariesResourceId: Int,
    onNavigateUp: () -> Unit
) {
    composable<SettingsLicensesScreenDestination> {
        SettingLicensesScreen(
            librariesResourceId = librariesResourceId,
            onNavigateUp = onNavigateUp
        )
    }
}