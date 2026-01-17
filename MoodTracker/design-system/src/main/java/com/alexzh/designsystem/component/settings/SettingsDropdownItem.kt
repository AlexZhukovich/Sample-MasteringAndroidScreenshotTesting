package com.alexzh.designsystem.component.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.icon.ArrowDropDownIcon

@Composable
fun <T> SettingsDropdownItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    options: List<T>,
    selectedOption: T,
    optionLabel: @Composable (T) -> String,
    onOptionSelected: (T) -> Unit,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

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

            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (enabled) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                )
            }
        }

        Box {
            Row(
                modifier = Modifier
                    .clickable(enabled = enabled) { expanded = true }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = optionLabel(selectedOption),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (enabled) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.primary.copy(alpha = 0.38f)
                )

                Icon(
                    imageVector = ArrowDropDownIcon,
                    contentDescription = null,
                    tint = if (enabled) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = optionLabel(option),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_SettingsDropdownItem_Enabled() {
    AppTheme {
        Surface {
            SettingsDropdownItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Icon Shape",
                description = "Choose the style for mood icons",
                options = listOf("Circle", "Rounded Square"),
                selectedOption = "Circle",
                optionLabel = { it },
                onOptionSelected = { }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_SettingsDropdownItem_NoDescription() {
    AppTheme {
        Surface {
            SettingsDropdownItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Icon Shape",
                options = listOf("Circle", "Rounded Square"),
                selectedOption = "Rounded Square",
                optionLabel = { it },
                onOptionSelected = { }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_SettingsDropdownItem_Disabled() {
    AppTheme {
        Surface {
            SettingsDropdownItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Icon Shape",
                description = "Choose the style for mood icons",
                options = listOf("Circle", "Rounded Square"),
                selectedOption = "Circle",
                optionLabel = { it },
                onOptionSelected = { },
                enabled = false
            )
        }
    }
}