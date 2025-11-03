package com.alexzh.moodtracker.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alexzh.designsystem.R as DesignSystemR
import com.alexzh.moodtracker.R

enum class AppNavigationItems(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    HOME(
        title = R.string.navigation_home_label,
        icon = DesignSystemR.drawable.ic_home
    ),
    STATISTICS(
        title = R.string.navigation_statistics_label,
        icon = DesignSystemR.drawable.ic_monitoring
    ),
    SETTINGS(
        title = R.string.navigation_settings_label,
        icon = DesignSystemR.drawable.ic_settings
    )
}