package com.alexzh.designsystem.component.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.R
import com.alexzh.designsystem.component.button.IconButton
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.icon.ArrowBackIcon
import com.alexzh.designsystem.icon.DeleteIcon
import com.alexzh.designsystem.icon.EditIcon
import com.alexzh.designsystem.icon.SettingsIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBackButton(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit,
    backButtonContentDescription: String = stringResource(R.string.topAppBar_backButton_contentDescription),
    backButtonIcon: ImageVector = ArrowBackIcon,
    backButtonEnabled: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = {
            IconButton(
                onClick = onBack,
                icon = backButtonIcon,
                contentDescription = backButtonContentDescription,
                enabled = backButtonEnabled
            )
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBackButton(
    modifier: Modifier = Modifier,
    titleContent: @Composable () -> Unit,
    onBack: () -> Unit,
    backButtonContentDescription: String = stringResource(R.string.topAppBar_backButton_contentDescription),
    backButtonIcon: ImageVector = ArrowBackIcon,
    backButtonEnabled: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = titleContent,
        navigationIcon = {
            IconButton(
                onClick = onBack,
                icon = backButtonIcon,
                contentDescription = backButtonContentDescription,
                enabled = backButtonEnabled
            )
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
fun TopAppBarAction(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        icon = icon,
        contentDescription = contentDescription,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun Preview_TopAppBars() {
    AppTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TopAppBar(
                title = "AppBar"
            )

            TopAppBar(
                title = "AppBar with actions",
                actions = {
                    TopAppBarAction(
                        icon = SettingsIcon,
                        onClick = {}
                    )
                }
            )

            TopAppBarWithBackButton(
                title = "AppBar with back",
                onBack = {},
                backButtonContentDescription = "Back"
            )

            TopAppBarWithBackButton(
                title = "AppBar with disabled back",
                backButtonEnabled = false,
                onBack = {},
                backButtonContentDescription = "Back"
            )

            TopAppBarWithBackButton(
                title = "AppBar with back & actions",
                onBack = {},
                backButtonContentDescription = "Back",
                actions = {
                    TopAppBarAction(
                        icon = SettingsIcon,
                        onClick = {},
                    )
                }
            )

            TopAppBarWithBackButton(
                titleContent = {
                    Column {
                        Text("Title")
                        Text("Subtitle")
                    }
                },
                onBack = {},
                actions = {
                    TopAppBarAction(
                        icon = EditIcon,
                        onClick = {},
                    )
                    TopAppBarAction(
                        icon = DeleteIcon,
                        onClick = {},
                    )
                }
            )
        }
    }
}