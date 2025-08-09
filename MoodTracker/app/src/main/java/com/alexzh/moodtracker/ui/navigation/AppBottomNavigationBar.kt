package com.alexzh.moodtracker.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R

enum class BottomNavigationItems(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    HOME(
        title = R.string.navigation_home_label,
        icon = R.drawable.ic_home
    ),
    STATISTICS(
        title = R.string.navigation_statistics_label,
        icon = R.drawable.ic_monitoring
    )
}

@Composable
fun AppBottomNavigationBar(
    selectedItem: BottomNavigationItems,
    onNavigateToHome: () -> Unit,
    onNavigateToStatistics: () -> Unit,
) {
    NavigationBar {
        BottomNavigationItems.entries.forEach { item ->
            val onClick = when (item) {
                BottomNavigationItems.HOME -> onNavigateToHome
                BottomNavigationItems.STATISTICS -> onNavigateToStatistics
            }

            NavigationBarItem(
                selected = item == selectedItem,
                onClick = {
                    onClick()
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.title),
                        tint = if (selectedItem == item) MaterialTheme.colorScheme.surface
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
