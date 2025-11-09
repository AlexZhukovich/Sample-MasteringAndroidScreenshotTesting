package com.alexzh.designsystem.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexzh.designsystem.R

@Composable
fun DeleteConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    dismissButtonText: String = stringResource(R.string.dialog_cancel_label),
    confirmButtonText: String = stringResource(R.string.dialog_delete_label),
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
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
