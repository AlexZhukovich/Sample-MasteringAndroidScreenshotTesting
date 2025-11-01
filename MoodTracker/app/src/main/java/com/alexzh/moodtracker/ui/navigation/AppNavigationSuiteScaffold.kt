package com.alexzh.moodtracker.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun AppNavigationSuiteScaffold(
    selectedItem: AppNavigationItems,
    onNavigateToHome: () -> Unit,
    onNavigateToStatistics: () -> Unit,
    onNavigateToSettings: () -> Unit,
    content: @Composable () -> Unit
) {
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppNavigationItems.entries.forEach { item ->
                val onClick = when (item) {
                    AppNavigationItems.HOME -> onNavigateToHome
                    AppNavigationItems.STATISTICS -> onNavigateToStatistics
                    AppNavigationItems.SETTINGS -> onNavigateToSettings
                }

                item(
                    selected = item == selectedItem,
                    onClick = onClick,
                    icon = {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = stringResource(item.title)
                        )
                    },
                    label = {
                        Text(text = stringResource(item.title))
                    }
                )
            }
        }
    ) {
        content()
    }
}