package com.alexzh.designsystem.component.selector.daterangeselector

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.R
import com.alexzh.designsystem.component.button.IconButton
import com.alexzh.designsystem.component.dialog.DatePickerDialog
import com.alexzh.designsystem.component.selector.PeriodSelector
import com.alexzh.designsystem.core.modifier.circleLayout
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.icon.DateRangeIcon
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Composable
fun DateRangeSelector(
    modifier: Modifier = Modifier,
    state: DateRangeSelectorState,
    animationConfig: DateRangeSelectorAnimationConfig = DateRangeSelectorAnimationConfig(),
    todayButtonLabel: String = stringResource(R.string.dateRangeSelector_todayButton_label),
    selectDateIcon: ImageVector = DateRangeIcon,
    selectDateDialogConfirmButtonLabel: String = stringResource(R.string.dialog_ok_label),
    selectDateDialogDismissButtonLabel: String = stringResource(R.string.dialog_cancel_label),
    lastDaysLabel: String = stringResource(R.string.dateRangeSelector_lastDays_label, state.dateRange.size),
    onDateChange: (LocalDate) -> Unit = {},
    previousPeriodContentDescription: String = stringResource(R.string.dateRangeSelector_previousPeriod_contentDescription),
    nextPeriodEnabledContentDescription: String = stringResource(R.string.dateRangeSelector_nextPeriodEnabled_contentDescription),
    nextPeriodDisabledContentDescription: String = stringResource(R.string.dateRangeSelector_nextPeriodDisabled_contentDescription),
    openDatePickerContentDescription: String = stringResource(R.string.dateRangeSelector_openDatePicker_contentDescription),
    selectDateContentDescription: @Composable (formattedDate: String) -> String = { formattedDate ->
        stringResource(R.string.dateRangeSelector_selectedDate_contentDescription, formattedDate)
    },
    futureDateContentDescription: @Composable (formattedDate: String) -> String = { formattedDate ->
        stringResource(R.string.dateRangeSelector_futureDateNotSelectable_contentDescription, formattedDate)
    },
    dayNameFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEEE"),
    dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d"),
) {
    val coroutineScope = rememberCoroutineScope()

    DateRangeSelectorContent(
        modifier = modifier,
        dateRange = state.dateRange,
        selectedDate = state.selectedDate,
        currentDate = state.currentDate,
        onDateSelected = { date, displayedRange -> state.onDateSelected(date, displayedRange) },
        onPeriodChanged = { direction -> state.onPeriodChanged(direction, coroutineScope) },
        todayButtonLabel = todayButtonLabel,
        selectDateIcon = selectDateIcon,
        selectDateDialogConfirmButtonLabel = selectDateDialogConfirmButtonLabel,
        selectDateDialogDismissButtonLabel = selectDateDialogDismissButtonLabel,
        lastDaysLabel = lastDaysLabel,
        showTodayButton = state.showTodayButton,
        canNavigateNext = state.canNavigateNext,
        navigationDirection = state.navigationDirection,
        formattedDateRange = state.formattedDateRange,
        isCurrentPeriod = state.isCurrentPeriod,
        animationConfig = animationConfig,
        previousPeriodContentDescription = previousPeriodContentDescription,
        nextPeriodEnabledContentDescription = nextPeriodEnabledContentDescription,
        nextPeriodDisabledContentDescription = nextPeriodDisabledContentDescription,
        openDatePickerContentDescription = openDatePickerContentDescription,
        dayNameFormatter = dayNameFormatter,
        dateFormatter = dateFormatter,
        selectDateContentDescription = selectDateContentDescription,
        futureDateContentDescription = futureDateContentDescription
    )

    LaunchedEffect(state.selectedDate) {
        onDateChange(state.selectedDate)
    }

    LaunchedEffect(state.navigationDirection) {
        if (state.navigationDirection != PeriodChangeDirection.NONE) {
            delay(animationConfig.delayMs)
            state.resetNavigationDirection()
        }
    }
}

@Composable
private fun DateRangeSelectorHeader(
    modifier: Modifier = Modifier,
    selectedRangeTitle: String,
    todayButtonLabel: String,
    selectDateIcon: ImageVector,
    onPreviousPeriod: () -> Unit,
    onNextPeriod: () -> Unit,
    onShowDatePicker: () -> Unit,
    onToday: () -> Unit,
    canNavigateNext: Boolean,
    showTodayButton: Boolean,
    previousPeriodContentDescription: String,
    nextPeriodEnabledContentDescription: String,
    nextPeriodDisabledContentDescription: String,
    openDatePickerContentDescription: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PeriodSelector(
            label = selectedRangeTitle,
            onPrevious = onPreviousPeriod,
            onNext = onNextPeriod,
            nextEnabled = canNavigateNext,
            previousContentDescription = previousPeriodContentDescription,
            nextEnabledContentDescription = nextPeriodEnabledContentDescription,
            nextDisabledContentDescription = nextPeriodDisabledContentDescription,
            textStyle = MaterialTheme.typography.bodyMedium,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showTodayButton) {
                TextButton(onClick = onToday) {
                    Text(todayButtonLabel)
                }
            }

            IconButton(
                modifier = Modifier.semantics {
                    contentDescription = openDatePickerContentDescription
                },
                onClick = onShowDatePicker,
                icon = selectDateIcon,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun DateRangeSelectorContent(
    modifier: Modifier = Modifier,
    dateRange: List<LocalDate>,
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate, List<LocalDate>) -> Unit,
    onPeriodChanged: (PeriodChangeDirection) -> Unit,
    todayButtonLabel: String,
    selectDateIcon: ImageVector,
    selectDateDialogConfirmButtonLabel: String,
    selectDateDialogDismissButtonLabel: String,
    lastDaysLabel: String,
    showTodayButton: Boolean,
    canNavigateNext: Boolean,
    navigationDirection: PeriodChangeDirection,
    formattedDateRange: String,
    isCurrentPeriod: Boolean,
    animationConfig: DateRangeSelectorAnimationConfig,
    previousPeriodContentDescription: String,
    nextPeriodEnabledContentDescription: String,
    nextPeriodDisabledContentDescription: String,
    openDatePickerContentDescription: String,
    dayNameFormatter: DateTimeFormatter,
    dateFormatter: DateTimeFormatter,
    selectDateContentDescription: @Composable (formattedDate: String) -> String,
    futureDateContentDescription: @Composable (formattedDate: String) -> String
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(
            horizontal =  8.dp,
            vertical = 8.dp
        )
    ) {
        val dateRangeTitle = if (isCurrentPeriod) { lastDaysLabel } else { formattedDateRange }

        DateRangeSelectorHeader(
            selectedRangeTitle = dateRangeTitle,
            todayButtonLabel = todayButtonLabel,
            selectDateIcon = selectDateIcon,
            onPreviousPeriod = { onPeriodChanged(PeriodChangeDirection.PREVIOUS) },
            onNextPeriod = { onPeriodChanged(PeriodChangeDirection.NEXT) },
            onShowDatePicker = { showDatePicker = true },
            onToday = { onDateSelected(currentDate, dateRange) },
            canNavigateNext = canNavigateNext,
            showTodayButton = showTodayButton,
            previousPeriodContentDescription = previousPeriodContentDescription,
            nextPeriodEnabledContentDescription = nextPeriodEnabledContentDescription,
            nextPeriodDisabledContentDescription = nextPeriodDisabledContentDescription,
            openDatePickerContentDescription = openDatePickerContentDescription
        )

        AnimatedContent(
            targetState = dateRange,
            transitionSpec = { animationConfig.getTransitionSpec(navigationDirection) },
            modifier = Modifier.fillMaxWidth()
        ) { currentDateRange ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        var totalDragAmount = 0f
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                totalDragAmount += dragAmount
                            },
                            onDragEnd = {
                                if (abs(totalDragAmount) > 100f) {
                                    if (totalDragAmount > 0) {
                                        onPeriodChanged(PeriodChangeDirection.PREVIOUS)
                                    } else {
                                        if (canNavigateNext) {
                                            onPeriodChanged(PeriodChangeDirection.NEXT)
                                        }
                                    }
                                }
                                totalDragAmount = 0f
                            }
                        )
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                currentDateRange.forEach { date ->
                    DateIndicator(
                        date = date,
                        isSelected = date == selectedDate,
                        isFutureDate = date > currentDate,
                        onClick = { onDateSelected(date, currentDateRange) },
                        dayNameFormatter = dayNameFormatter,
                        dateFormatter = dateFormatter,
                        selectDateContentDescription = selectDateContentDescription,
                        futureDateContentDescription = futureDateContentDescription
                    )
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            selectedDate = selectedDate,
            confirmButtonLabel = selectDateDialogConfirmButtonLabel,
            dismissButtonLabel = selectDateDialogDismissButtonLabel,
            onDateSelected = { date ->
                onDateSelected(date, dateRange)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false },
            currentDate = currentDate
        )
    }
}

@Composable
private fun DateIndicator(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean,
    isFutureDate: Boolean,
    onClick: () -> Unit,
    dayNameFormatter: DateTimeFormatter,
    dateFormatter: DateTimeFormatter,
    selectDateContentDescription: @Composable (formattedDate: String) -> String,
    futureDateContentDescription: @Composable (formattedDate: String) -> String
) {
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val interactionSource = remember { MutableInteractionSource() }
    val dayName = remember(date) { date.format(dayNameFormatter) }
    val dayNumber = remember(date) { date.dayOfMonth.toString() }

    val formattedDate = remember(date) { date.format(dateFormatter) }

    val selectDateDescription = selectDateContentDescription(formattedDate)
    val futureDateDescription = futureDateContentDescription(formattedDate)

    Surface(
        modifier = modifier
            .width(48.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true),
                onClick = if (isFutureDate) { {} } else onClick,
            )
            .semantics {
                contentDescription = if (isFutureDate) {
                    futureDateDescription
                } else {
                    selectDateDescription
                }
            },
        color = containerColor,
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = dayName,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(1.dp)
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Transparent,
                        shape = CircleShape
                    )
                    .circleLayout(),
                text = dayNumber,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_DateRangeSelector_SelectedDayIsTheSameAsCurrentDay() {
    val date = LocalDate.of(2025, 1, 15)
    val state = rememberDateRangeSelectorState(
        currentDate = date,
        selectedDate = date,
        daysCount = 7
    )

    AppTheme {
        Surface {
            DateRangeSelector(state = state)
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_DateRangeSelector_SelectedDayIsDifferentFromCurrentDay() {
    val date = LocalDate.of(2025, 1, 15)
    val state = rememberDateRangeSelectorState(
        currentDate = date,
        selectedDate = date.minusDays(2),
        daysCount = 7
    )

    AppTheme {
        Surface {
            DateRangeSelector(state = state)
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_DateRangeSelector_SelectedDayIsTenDaysBeforeCurrentDay() {
    val date = LocalDate.of(2025, 1, 15)
    val state = rememberDateRangeSelectorState(
        currentDate = date,
        selectedDate = date.minusDays(10),
        daysCount = 7
    )

    AppTheme {
        Surface {
            DateRangeSelector(state = state)
        }
    }
}