package com.alexzh.designsystem.component.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.core.theme.AppTheme

@Composable
fun SettingsSwitchItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String?,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (enabled) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            )
            description?.let { desc ->
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (enabled) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}

@PreviewLightDark
@Composable
fun Preview_SettingsSwitchItem_Enabled() {
    AppTheme {
        SettingsSwitchItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Switch Item Title",
            description = "Switch Item Description",
            checked = true,
            onCheckedChange = { _ -> }
        )
    }
}

@PreviewLightDark
@Composable
fun Preview_SettingsSwitchItem_Disabled() {
    AppTheme {
        SettingsSwitchItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Switch Item Title",
            description = "Switch Item Description",
            checked = false,
            onCheckedChange = { _ -> },
            enabled = false
        )
    }
}