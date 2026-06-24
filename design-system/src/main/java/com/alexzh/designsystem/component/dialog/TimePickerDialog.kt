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
    onDismiss: () -> Unit,
    is24Hour: Boolean = DateFormat.is24HourFormat(LocalContext.current)
) {
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
fun Preview_TimePickerDialog_12hFormat() {
    AppTheme {
        TimePickerDialog(
            selectedTime = LocalTime.of(16, 15),
            onTimeSelected = {},
            onDismiss = {},
            is24Hour = false
        )
    }
}

@PreviewLightDark
@Composable
fun Preview_TimePickerDialog_24hFormat() {
    AppTheme {
        TimePickerDialog(
            selectedTime = LocalTime.of(16, 15),
            onTimeSelected = {},
            onDismiss = {},
            is24Hour = true
        )
    }
}