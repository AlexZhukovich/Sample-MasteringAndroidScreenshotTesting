package com.alexzh.designsystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.icon.AddIcon
import com.alexzh.designsystem.icon.SettingsIcon

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Text(text = text)
    }
}

@Composable
fun PrimaryIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String? = null
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String? = null,
    enabled: Boolean = true
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}

@PreviewLightDark
@Composable
fun Preview_Buttons() {
    AppTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text("Enabled & Disabled primary buttons")
                PrimaryButton(
                    onClick = {},
                    text = "Enabled Primary Button"
                )
                PrimaryButton(
                    onClick = {},
                    text = "Disabled Primary Button",
                    enabled = false
                )
                Text("Icon buttons")
                PrimaryIconButton(
                    onClick = {},
                    icon = AddIcon,
                    contentDescription = null
                )
                IconButton(
                    onClick = {},
                    icon = SettingsIcon,
                    contentDescription = null
                )
            }
        }
    }
}