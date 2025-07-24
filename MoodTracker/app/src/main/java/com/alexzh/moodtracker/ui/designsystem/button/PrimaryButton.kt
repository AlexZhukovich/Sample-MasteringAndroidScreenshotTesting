package com.alexzh.moodtracker.ui.designsystem.button

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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