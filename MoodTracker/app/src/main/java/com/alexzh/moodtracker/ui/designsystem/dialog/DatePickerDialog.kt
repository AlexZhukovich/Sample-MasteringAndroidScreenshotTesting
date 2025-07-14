package com.alexzh.moodtracker.ui.designsystem.dialog

import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.theme.AppTheme
import java.time.LocalDate
import androidx.compose.material3.DatePickerDialog as Material3DatePickerDialog

private const val MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    currentDate: LocalDate = LocalDate.now()
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toEpochDay() * MILLISECONDS_PER_DAY,
        initialDisplayedMonthMillis = selectedDate.toEpochDay() * MILLISECONDS_PER_DAY,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val date = LocalDate.ofEpochDay(utcTimeMillis / MILLISECONDS_PER_DAY)
                return date <= currentDate
            }
        }
    )

    Material3DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = LocalDate.ofEpochDay(millis / MILLISECONDS_PER_DAY)
                        onDateSelected(date)
                    }
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
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}

@PreviewLightDark
@Composable
private fun DatePickerDialogPreview() {
    AppTheme {
        DatePickerDialog(
            selectedDate = LocalDate.now(),
            onDateSelected = {},
            onDismiss = {},
            currentDate = LocalDate.now()
        )
    }
}