package com.alexzh.moodtracker.ui.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.settings.SettingsInfoItem
import com.alexzh.moodtracker.ui.designsystem.settings.SettingsNavigationItem
import com.alexzh.moodtracker.ui.designsystem.settings.SettingsSectionTitle
import com.alexzh.moodtracker.ui.designsystem.settings.SettingsSwitchItem

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel,
    onNavigateUp: () -> Unit,
    onNavigateToManageActions: () -> Unit,
    onNavigateToThirdPartyLicenses: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreenContent(
        uiState = uiState,
        onDynamicColorsChange = { enabled ->
            viewModel.onEvent(SettingsScreenEvent.OnDynamicColorsChanged(enabled))
        },
        onNavigateUp = onNavigateUp,
        onNavigateToManageActions = onNavigateToManageActions,
        onNavigateToThirdPartyLicenses = onNavigateToThirdPartyLicenses
    )
}

@Composable
fun SettingsScreenContent(
    uiState: SettingsScreenUiState,
    onDynamicColorsChange: (Boolean) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateToManageActions: () -> Unit,
    onNavigateToThirdPartyLicenses: () -> Unit
) {
    Scaffold(
        topBar = {
            SettingsScreenTopAppBar(
                onNavigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            SettingsSectionTitle(
                title = stringResource(R.string.settingsScreen_generalSection_title),
            )
            SettingsSwitchItem(
                title = stringResource(R.string.settingsScreen_dynamicColors_title),
                description = stringResource(R.string.settingsScreen_dynamicColors_description),
                checked = uiState.isDynamicColorsEnabled,
                onCheckedChange = onDynamicColorsChange,
                enabled = !uiState.isLoading
            )
            SettingsNavigationItem(
                title = stringResource(R.string.settingsScreen_manageActions_title),
                onClick = onNavigateToManageActions
            )
            SettingsSectionTitle(
                title = stringResource(R.string.settingsScreen_aboutSection_title),
            )
            SettingsNavigationItem(
                title = stringResource(R.string.settingsScreen_thirdPartyLicenses_title),
                onClick = onNavigateToThirdPartyLicenses
            )
            SettingsInfoItem(
                title = stringResource(R.string.settingsScreen_version_title),
                value = uiState.appVersion
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(R.string.settingsScreen_title))
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.common_navigateUp_contentDescription)
                )
            }
        },
    )
}