package com.alexzh.moodtracker.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.alexzh.designsystem.icon.HomeIcon
import com.alexzh.designsystem.icon.MonitoringIcon
import com.alexzh.designsystem.icon.SettingsIcon
import com.alexzh.moodtracker.R

enum class AppNavigationItems(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    HOME(
        title = R.string.navigation_home_label,
        icon = HomeIcon
    ),
    STATISTICS(
        title = R.string.navigation_statistics_label,
        icon = MonitoringIcon
    ),
    SETTINGS(
        title = R.string.navigation_settings_label,
        icon = SettingsIcon
    )
}