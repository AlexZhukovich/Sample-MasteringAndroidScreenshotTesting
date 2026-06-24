package com.alexzh.designsystem.component.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.icon.HomeIcon
import com.alexzh.designsystem.icon.MonitoringIcon
import com.alexzh.designsystem.icon.SettingsIcon

@Composable
fun AppNavigationSuiteScaffold(
    items: List<BottomNavigationItem>,
    selectedItemIndex: Int,
    content: @Composable () -> Unit
) {
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            items.forEachIndexed { index, item ->
                item(
                    selected = index == selectedItemIndex,
                    onClick = item.onClick,
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                    },
                    label = {
                        Text(text = item.label)
                    }
                )
            }
        },
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationRailContainerColor = MaterialTheme.colorScheme.background,
            navigationRailContentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        content()
    }
}

@Preview(device = PHONE, showBackground = true)
@Preview(device = PHONE, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(device = PIXEL_TABLET, showBackground = true)
@Preview(device = PIXEL_TABLET, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Preview_AppNavigationSuiteScaffold() {
    val navigationItems = listOf(
        BottomNavigationItem(
            label = "Home",
            icon = HomeIcon,
            onClick = { }
        ),
        BottomNavigationItem(
            label = "Statistics",
            icon = MonitoringIcon,
            onClick = { }
        ),
        BottomNavigationItem(
            label = "Settings",
            icon = SettingsIcon,
            onClick = { }
        )
    )

    AppTheme {
        AppNavigationSuiteScaffold(
            items = navigationItems,
            selectedItemIndex = 0
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Content Area",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}