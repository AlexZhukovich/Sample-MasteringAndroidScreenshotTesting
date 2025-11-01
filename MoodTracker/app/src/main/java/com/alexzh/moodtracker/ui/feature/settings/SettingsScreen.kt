package com.alexzh.moodtracker.ui.feature.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.bars.TopAppBarWithBackButton
import com.alexzh.moodtracker.ui.designsystem.settings.SettingsDropdownItem
import com.alexzh.moodtracker.ui.designsystem.settings.SettingsInfoItem
import com.alexzh.moodtracker.ui.designsystem.settings.SettingsNavigationItem
import com.alexzh.moodtracker.ui.designsystem.settings.SettingsSectionTitle
import com.alexzh.moodtracker.ui.designsystem.settings.SettingsSwitchItem
import com.alexzh.moodtracker.ui.model.LocalizedIconShape
import com.alexzh.moodtracker.ui.theme.AppTheme

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
        onIconShapeChange = { iconShape ->
            viewModel.onEvent(SettingsScreenEvent.OnIconShapeChanged(iconShape))
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
    onIconShapeChange: (LocalizedIconShape) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateToManageActions: () -> Unit,
    onNavigateToThirdPartyLicenses: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarWithBackButton(
                title = stringResource(R.string.settingsScreen_title),
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
            SettingsDropdownItem(
                title = stringResource(R.string.settingsScreen_iconShape_title),
                description = stringResource(R.string.settingsScreen_iconShape_description),
                options = LocalizedIconShape.entries,
                selectedOption = uiState.iconShape,
                optionLabel = { stringResource(it.label) },
                onOptionSelected = onIconShapeChange,
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

@Preview(name = "Phone - Light", device = PHONE, showBackground = true)
@Preview(name = "Phone - Dark", device = PHONE, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Tablet - Light", device = PIXEL_TABLET, showBackground = true)
@Preview(name = "Tablet - Dark", device = PIXEL_TABLET, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview_SettingsScreenContent() {
    val uiState = SettingsScreenUiState(
        isLoading = false,
        isDynamicColorsEnabled = false,
        iconShape = LocalizedIconShape.ROUNDED_SQUARE,
        appVersion = "1.0.0"
    )

    AppTheme {
        SettingsScreenContent(
            uiState = uiState,
            onDynamicColorsChange = { },
            onIconShapeChange = { },
            onNavigateUp = { },
            onNavigateToManageActions = { },
            onNavigateToThirdPartyLicenses = { }
        )
    }
}