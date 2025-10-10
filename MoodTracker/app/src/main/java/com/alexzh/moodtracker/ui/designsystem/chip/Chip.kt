package com.alexzh.moodtracker.ui.designsystem.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.ui.theme.AppTheme

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    size: ChipSize = ChipSize.Medium
) {
    val horizontalPadding = when (size) {
        ChipSize.Small -> 12.dp
        ChipSize.Medium -> 16.dp
    }

    val verticalPadding = when (size) {
        ChipSize.Small -> 6.dp
        ChipSize.Medium -> 8.dp
    }

    val textStyle = when (size) {
        ChipSize.Small -> MaterialTheme.typography.labelMedium
        ChipSize.Medium -> MaterialTheme.typography.labelLarge
    }

    val backgroundColor = when (selected) {
        true -> MaterialTheme.colorScheme.secondaryContainer
        false -> MaterialTheme.colorScheme.surface
    }

    val contentColor = when (selected) {
        true -> MaterialTheme.colorScheme.onSecondaryContainer
        false -> MaterialTheme.colorScheme.onSurface
    }

    val borderColor = when (selected) {
        true -> MaterialTheme.colorScheme.primary
        false -> MaterialTheme.colorScheme.outline
    }

    Surface(
        modifier = modifier,
        onClick = onClick ?: {},
        enabled = onClick != null,
        shape = CircleShape,
        color = backgroundColor,
        contentColor = contentColor,
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        )
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            ),
            text = text,
            style = textStyle
        )
    }
}

enum class ChipSize {
    Small,
    Medium
}

@Preview
@Composable
fun Preview_Chip() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Non-clickable")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Chip(text = "Meditation", size = ChipSize.Small)
                Chip(text = "Exercise", size = ChipSize.Medium)
            }

            Text("Clickable - unselected")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Chip(
                    text = "Running",
                    selected = false,
                    onClick = { },
                    size = ChipSize.Small
                )
                Chip(
                    text = "Yoga",
                    selected = false,
                    onClick = { },
                    size = ChipSize.Medium
                )
            }

            Text("Clickable - selected")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Chip(
                    text = "Walking",
                    selected = true,
                    onClick = { },
                    size = ChipSize.Small
                )
                Chip(
                    text = "Reading",
                    selected = true,
                    onClick = { },
                    size = ChipSize.Medium
                )
            }
        }
    }
}