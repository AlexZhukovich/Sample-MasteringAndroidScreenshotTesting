package com.alexzh.designsystem.component.dialog

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.alexzh.designsystem.R
import com.alexzh.designsystem.core.theme.AppTheme
import java.time.LocalDate
import androidx.compose.material3.DatePickerDialog as Material3DatePickerDialog

private const val MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    selectedDate: LocalDate,
    confirmButtonLabel: String = stringResource(R.string.dialog_ok_label),
    dismissButtonLabel: String = stringResource(R.string.dialog_cancel_label),
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    currentDate: LocalDate? = LocalDate.now()
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

    DatePickerDialogContent(
        state = datePickerState,
        confirmButtonLabel = confirmButtonLabel,
        dismissButtonLabel = dismissButtonLabel,
        onDateSelected = onDateSelected,
        onDismiss = onDismiss
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    selectedDate: LocalDate,
    confirmButtonLabel: String,
    dismissButtonLabel: String,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toEpochDay() * MILLISECONDS_PER_DAY,
        initialDisplayedMonthMillis = selectedDate.toEpochDay() * MILLISECONDS_PER_DAY,
    )
    DatePickerDialogContent(
        state = datePickerState,
        confirmButtonLabel = confirmButtonLabel,
        dismissButtonLabel = dismissButtonLabel,
        onDateSelected = onDateSelected,
        onDismiss = onDismiss
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialogContent(
    state: DatePickerState,
    confirmButtonLabel: String,
    dismissButtonLabel: String,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {
    Material3DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    state.selectedDateMillis?.let { millis ->
                        val date = LocalDate.ofEpochDay(millis / MILLISECONDS_PER_DAY)
                        onDateSelected(date)
                    }
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
    ) {
        DatePicker(
            state = state,
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_DatePickerDialog_DifferentCurrentAndSelectedDates() {
    AppTheme {
        DatePickerDialog(
            selectedDate = LocalDate.of(2025, 1, 1),
            onDateSelected = {},
            onDismiss = {},
            currentDate = LocalDate.of(2025, 1, 5)
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_DatePickerDialog_SameCurrentAndSelectedDates() {
    AppTheme {
        DatePickerDialog(
            selectedDate = LocalDate.of(2025, 1, 5),
            onDateSelected = {},
            onDismiss = {},
            currentDate = LocalDate.of(2025, 1, 5)
        )
    }
}