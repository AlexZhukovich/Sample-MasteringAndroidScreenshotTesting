package com.alexzh.moodtracker.ui.feature.home.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexzh.moodtracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateToSettings: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(stringResource(R.string.homeScreen_title)) },
        actions = {
            IconButton(onClick = onNavigateToSettings) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = stringResource(R.string.navigation_settings_label)
                )
            }
        }
    )
}