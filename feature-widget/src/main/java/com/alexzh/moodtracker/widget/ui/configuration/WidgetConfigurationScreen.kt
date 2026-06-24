package com.alexzh.moodtracker.widget.ui.configuration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import com.alexzh.designsystem.component.settings.SettingsSliderItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.component.bars.TopAppBarWithBackButton
import com.alexzh.designsystem.component.button.IconButton
import com.alexzh.designsystem.component.settings.SettingsDropdownItem
import com.alexzh.designsystem.icon.CheckIcon
import com.alexzh.designsystem.icon.CloseIcon
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.widget.model.LocalizedWidgetTheme
import com.alexzh.moodtracker.widget.R
import com.alexzh.moodtracker.common.ui.R as CommonUiR
import com.alexzh.designsystem.R as DesignSystemR
import com.alexzh.designsystem.core.theme.darkScheme
import com.alexzh.designsystem.core.theme.lightScheme
import com.alexzh.moodtracker.common.ui.model.LocalizedIconShape

@Composable
fun WidgetConfigurationScreen(
    viewModel: WidgetConfigurationViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WidgetConfigurationContent(
        uiState = uiState,
        onTransparencyChanged = { viewModel.onEvent(WidgetConfigurationEvent.OnTransparencyChanged(it)) },
        onIconShapeChanged = { viewModel.onEvent(WidgetConfigurationEvent.OnIconShapeChanged(it)) },
        onThemeChanged = { viewModel.onEvent(WidgetConfigurationEvent.OnThemeChanged(it)) },
        onCancel = { viewModel.onEvent(WidgetConfigurationEvent.OnCancel) },
        onApply = { viewModel.onEvent(WidgetConfigurationEvent.OnApply) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetConfigurationContent(
    uiState: WidgetConfigurationUiState,
    onTransparencyChanged: (Float) -> Unit,
    onIconShapeChanged: (LocalizedIconShape) -> Unit,
    onThemeChanged: (LocalizedWidgetTheme) -> Unit,
    onCancel: () -> Unit,
    onApply: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarWithBackButton(
                title = stringResource(R.string.widgetConfigurationScreen_title),
                onBack = onCancel,
                backButtonContentDescription = stringResource(DesignSystemR.string.closeButton_contentDescription),
                backButtonIcon = CloseIcon,
                actions = {
                    IconButton(
                        onClick = onApply,
                        icon = CheckIcon,
                        contentDescription = stringResource(DesignSystemR.string.applyButton_contentDescription)
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
            ) {
                WidgetPreview(
                    modifier = Modifier.fillMaxWidth()
                        .height(100.dp),
                    transparency = uiState.transparency,
                    iconShape = uiState.iconShape,
                    theme = uiState.theme
                )
            }

            SettingsDropdownItem(
                title = stringResource(R.string.widgetConfigurationScreen_theme_label),
                options = LocalizedWidgetTheme.entries,
                selectedOption = uiState.theme,
                optionLabel = { stringResource(it.label) },
                onOptionSelected = onThemeChanged
            )

            SettingsDropdownItem(
                title = stringResource(CommonUiR.string.common_iconShape_label),
                options = LocalizedIconShape.entries,
                selectedOption = uiState.iconShape,
                optionLabel = { stringResource(it.label) },
                onOptionSelected = onIconShapeChanged
            )

            val opacityValue = stringResource(
                R.string.widgetConfigurationScreen_opacity_value_label,
                ((1f - uiState.transparency) * 100).toInt()
            )
            SettingsSliderItem(
                title = stringResource(R.string.widgetConfigurationScreen_opacity_label),
                value = 1f - uiState.transparency,
                onValueChange = { onTransparencyChanged(1f - it) },
                valueLabel = { opacityValue },
                steps = 19
            )
        }
    }
}

@Composable
fun WidgetPreview(
    modifier: Modifier = Modifier,
    transparency: Float = 0f,
    iconShape: LocalizedIconShape = LocalizedIconShape.CIRCLE,
    theme: LocalizedWidgetTheme = LocalizedWidgetTheme.LIGHT
) {
    val backgroundAlpha = 1f - transparency
    val colorScheme = if (theme == LocalizedWidgetTheme.LIGHT) lightScheme else darkScheme

    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(colorScheme.background.copy(alpha = backgroundAlpha))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            text = stringResource(R.string.widget_title),
            color = colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LocalizedMood.entries.forEach { mood ->
                Image(
                    painter = painterResource(id = mood.getIcon(iconShape.toIconShape())),
                    contentDescription = stringResource(id = mood.label),
                    modifier = Modifier.size(48.dp).padding(4.dp)
                )
            }
        }
    }
}

@Preview(device = PHONE, showBackground = true)
@Preview(device = PHONE, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(device = PIXEL_TABLET, showBackground = true)
@Preview(device = PIXEL_TABLET, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Preview_WidgetConfigurationScreen() {
    val uiState = WidgetConfigurationUiState(
        transparency = 0.3f,
        iconShape = LocalizedIconShape.CIRCLE,
        theme = LocalizedWidgetTheme.LIGHT
    )

    AppTheme {
        WidgetConfigurationContent(
            uiState = uiState,
            onTransparencyChanged = { },
            onIconShapeChanged = { },
            onThemeChanged = { },
            onCancel = { },
            onApply = { }
        )
    }
}