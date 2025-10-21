package com.alexzh.moodtracker.ui.designsystem.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexzh.moodtracker.R

@Composable
fun FormDialog(
    modifier: Modifier = Modifier,
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String = stringResource(R.string.common_save_label),
    confirmButtonEnabled: Boolean = true,
    dismissButtonText: String = stringResource(R.string.common_cancel_label),
    content: @Composable () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { content() },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = confirmButtonEnabled
            ) {
                Text(text = confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dismissButtonText)
            }
        }
    )
}