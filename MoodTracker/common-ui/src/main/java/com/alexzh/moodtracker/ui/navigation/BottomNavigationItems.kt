package com.alexzh.moodtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alexzh.designsystem.component.navigation.BottomNavigationItem
import com.alexzh.designsystem.icon.HomeIcon
import com.alexzh.designsystem.icon.MonitoringIcon
import com.alexzh.designsystem.icon.SettingsIcon
import com.alexzh.moodtracker.common.ui.R

@Composable
fun defaultBottomNavigationItems(
    onNavigateToHome: () -> Unit,
    onNavigateToStatistics: () -> Unit,
    onNavigateToSettings: () -> Unit
): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = stringResource(R.string.navigation_home_label),
            icon = HomeIcon,
            onClick = onNavigateToHome
        ),
        BottomNavigationItem(
            label = stringResource(R.string.navigation_statistics_label),
            icon = MonitoringIcon,
            onClick = onNavigateToStatistics
        ),
        BottomNavigationItem(
            label = stringResource(R.string.navigation_settings_label),
            icon = SettingsIcon,
            onClick = onNavigateToSettings
        )
    )
}