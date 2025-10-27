package com.alexzh.moodtracker.ui.designsystem.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun SettingsNavigationItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onClick() }
            .padding(vertical = 8.dp),
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
}

@PreviewLightDark
@Composable
fun Preview_SettingsNavigationItem_Enabled() {
    AppTheme {
        SettingsNavigationItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Navigation Item Title",
            description = "Navigation Item Description",
            onClick = { }
        )
    }
}

@PreviewLightDark
@Composable
fun Preview_SettingsNavigationItem_Disabled() {
    AppTheme {
        SettingsNavigationItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Navigation Item Title",
            onClick = { },
            enabled = false
        )
    }
}