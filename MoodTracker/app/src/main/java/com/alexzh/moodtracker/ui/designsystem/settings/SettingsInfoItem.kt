package com.alexzh.moodtracker.ui.designsystem.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.ui.theme.AppTheme

@Composable
fun SettingsInfoItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    description: String? = null,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
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
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = if (enabled) MaterialTheme.colorScheme.onSurfaceVariant
            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
        )
    }
}

@PreviewLightDark
@Composable
fun Preview_SettingsInfoItem_Enabled() {
    AppTheme {
        SettingsInfoItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Info Item Title",
            value = "Value",
            description = "Info Item Description"
        )
    }
}

@PreviewLightDark
@Composable
fun Preview_SettingsInfoItem_Disabled() {
    AppTheme {
        SettingsInfoItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Info Item Title",
            value = "Value",
            description = "Info Item Description",
            enabled = false
        )
    }
}