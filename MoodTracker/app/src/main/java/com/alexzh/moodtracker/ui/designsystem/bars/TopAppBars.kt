package com.alexzh.moodtracker.ui.designsystem.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.button.IconButton
import com.alexzh.moodtracker.ui.theme.AppTheme
import kotlin.Unit

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
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBackButton(
    modifier: Modifier = Modifier,
    title: String,
    onNavigateUp: () -> Unit,
    backButtonEnabled: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = {
            IconButton(
                onClick = onNavigateUp,
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = stringResource(R.string.common_navigateUp_contentDescription),
                enabled = backButtonEnabled
            )
        },
        actions = actions
    )
}

@Composable
fun TopAppBarAction(
    modifier: Modifier = Modifier,
    icon: Painter,
    onClick: () -> Unit,
    contentDescription: String? = null
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        painter = icon,
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
                        icon = painterResource(R.drawable.ic_settings),
                        onClick = {}
                    )
                }
            )

            TopAppBarWithBackButton(
                title = "AppBar with back",
                onNavigateUp = {}
            )

            TopAppBarWithBackButton(
                title = "AppBar with disabled back",
                backButtonEnabled = false,
                onNavigateUp = {}
            )

            TopAppBarWithBackButton(
                title = "AppBar with back & actions",
                onNavigateUp = {},
                actions = {
                    TopAppBarAction(
                        icon = painterResource(R.drawable.ic_settings),
                        onClick = {},
                    )
                }
            )
        }
    }
}