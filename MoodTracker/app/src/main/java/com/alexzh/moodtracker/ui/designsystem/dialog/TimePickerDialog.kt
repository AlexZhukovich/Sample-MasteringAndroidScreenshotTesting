package com.alexzh.moodtracker.ui.designsystem.dialog

import android.text.format.DateFormat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.theme.AppTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    selectedTime: LocalTime,
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
                Text(stringResource(R.string.common_ok_label))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.common_cancel_label))
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