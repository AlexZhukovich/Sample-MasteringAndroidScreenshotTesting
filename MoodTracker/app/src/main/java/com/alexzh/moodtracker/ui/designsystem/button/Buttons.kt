package com.alexzh.moodtracker.ui.designsystem.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.theme.AppTheme

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
    painter: Painter,
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
            painter = painter,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    painter: Painter,
    contentDescription: String? = null,
    enabled: Boolean = true
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription
        )
    }
}

@PreviewLightDark
@Composable
fun ButtonsPreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
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
                painter = painterResource(R.drawable.ic_add),
                contentDescription = null
            )
            IconButton(
                onClick = {},
                painter = painterResource(R.drawable.ic_add),
                contentDescription = null
            )
        }
    }
}