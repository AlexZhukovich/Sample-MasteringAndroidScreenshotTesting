package com.alexzh.designsystem.component.dialog

import android.text.format.DateFormat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.R
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    selectedTime: LocalTime,
    confirmButtonLabel: String = stringResource(R.string.dialog_ok_label),
    dismissButtonLabel: String = stringResource(R.string.dialog_cancel_label),
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val is24Hour = DateFormat.is24HourFormat(context)
    
    val timePickerState = rememberTimePickerState(
        initialHour = selectedTime.hour,
        initialMinute = selectedTime.minute,
        is24Hour = is24Hour
    )
    
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(LocalTime.of(timePickerState.hour, timePickerState.minute))
                    onDismiss()
                }
            ) {
                Text(confirmButtonLabel)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissButtonLabel)
            }
        },
        text = {
            TimePicker(state = timePickerState)
        }
    )
}

@PreviewLightDark
@Composable
private fun TimePickerDialogPreview() {
    AppTheme {
        TimePickerDialog(
            selectedTime = LocalTime.now(),
            onTimeSelected = {},
            onDismiss = {}
        )
    }
}