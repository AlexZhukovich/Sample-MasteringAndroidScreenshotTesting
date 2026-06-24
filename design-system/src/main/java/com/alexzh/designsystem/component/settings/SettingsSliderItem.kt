package com.alexzh.designsystem.component.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.core.theme.AppTheme

@Composable
fun SettingsSliderItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    valueLabel: (Float) -> String,
    steps: Int = 0,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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

                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (enabled) MaterialTheme.colorScheme.onSurfaceVariant
                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                    )
                }
            }

            Text(
                text = valueLabel(value),
                style = MaterialTheme.typography.bodyMedium,
                color = if (enabled) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.primary.copy(alpha = 0.38f)
            )
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            enabled = enabled
        )
    }
}

@PreviewLightDark
@Composable
fun Preview_SettingsSliderItem_Enabled() {
    AppTheme {
        Surface {
            SettingsSliderItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Opacity",
                description = "Adjust the background opacity",
                value = 0.75f,
                onValueChange = { },
                valueLabel = { "${(it * 100).toInt()}%" }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_SettingsSliderItem_Disabled_NoDescription() {
    AppTheme {
        Surface {
            SettingsSliderItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Opacity",
                value = 0.75f,
                onValueChange = { },
                valueLabel = { "${(it * 100).toInt()}%" },
                enabled = false
            )
        }
    }
}