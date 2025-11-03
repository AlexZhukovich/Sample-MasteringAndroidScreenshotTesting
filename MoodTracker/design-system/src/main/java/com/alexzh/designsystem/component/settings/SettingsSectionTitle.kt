package com.alexzh.designsystem.component.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.core.theme.AppTheme

@Composable
fun SettingsSectionTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        text = title,
        modifier = modifier.padding(vertical = 8.dp),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary
    )
}

@PreviewLightDark
@Composable
fun Preview_SettingsTitleItem() {
    AppTheme {
        SettingsSectionTitle(
            modifier = Modifier.fillMaxWidth(),
            title = "General"
        )
    }
}