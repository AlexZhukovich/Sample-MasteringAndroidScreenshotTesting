package com.alexzh.designsystem.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.alexzh.designsystem.R
import com.alexzh.designsystem.core.theme.AppTheme

@Composable
fun FormDialog(
    modifier: Modifier = Modifier,
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String = stringResource(R.string.dialog_save_label),
    confirmButtonEnabled: Boolean = true,
    dismissButtonText: String = stringResource(R.string.dialog_cancel_label),
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

@PreviewLightDark
@Composable
fun Preview_FormDialog() {
    AppTheme {
        FormDialog(
            title = "Enter name",
            onDismiss = {},
            onConfirm = {}
        ) {
            Column {
                OutlinedTextField(
                    label = { Text("Name") },
                    value = "",
                    onValueChange = {}
                )
            }
        }
    }
}