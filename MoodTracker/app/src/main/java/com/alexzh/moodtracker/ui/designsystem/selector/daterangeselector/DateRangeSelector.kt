package com.alexzh.moodtracker.ui.designsystem.selector.daterangeselector

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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.button.IconButton
import com.alexzh.moodtracker.ui.designsystem.core.modifier.circleLayout
import com.alexzh.moodtracker.ui.designsystem.dialog.DatePickerDialog
import com.alexzh.moodtracker.ui.designsystem.selector.PeriodSelector
import com.alexzh.moodtracker.ui.theme.AppTheme
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Composable
fun DateRangeSelector(
    modifier: Modifier = Modifier,
    state: DateRangeSelectorState,
    animationConfig: DateRangeSelectorAnimationConfig = DateRangeSelectorAnimationConfig(),
    onDateChange: (LocalDate) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    DateRangeSelectorContent(
        modifier = modifier,
        dateRange = state.dateRange,
        selectedDate = state.selectedDate,
        currentDate = state.currentDate,
        onDateSelected = { date, displayedRange -> state.onDateSelected(date, displayedRange) },
        onPeriodChanged = { direction -> state.onPeriodChanged(direction, coroutineScope) },
        showTodayButton = state.showTodayButton,
        canNavigateNext = state.canNavigateNext,
        navigationDirection = state.navigationDirection,
        formattedDateRange = state.formattedDateRange,
        isCurrentPeriod = state.isCurrentPeriod,
        animationConfig = animationConfig
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
    onPreviousPeriod: () -> Unit,
    onNextPeriod: () -> Unit,
    onShowDatePicker: () -> Unit,
    onToday: () -> Unit,
    canNavigateNext: Boolean,
    showTodayButton: Boolean,
    previousPeriodContentDescription: String = stringResource(R.string.designSystem_dateRangeSelector_previousPeriod_contentDescription),
    nextPeriodEnabledContentDescription: String = stringResource(R.string.designSystem_dateRangeSelector_nextPeriodEnabled_contentDescription),
    nextPeriodDisabledContentDescription: String = stringResource(R.string.designSystem_dateRangeSelector_nextPeriodDisabled_contentDescription),
    openDatePickerContentDescription: String = stringResource(R.string.designSystem_dateRangeSelector_openDatePicker_contentDescription),
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
                    Text(stringResource(R.string.designSystem_dateRangeSelector_todayButton_label))
                }
            }

            IconButton(
                modifier = Modifier.semantics {
                    contentDescription = openDatePickerContentDescription
                },
                onClick = onShowDatePicker,
                painter = painterResource(R.drawable.ic_date_range),
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
    showTodayButton: Boolean,
    canNavigateNext: Boolean,
    navigationDirection: PeriodChangeDirection,
    formattedDateRange: String,
    isCurrentPeriod: Boolean,
    animationConfig: DateRangeSelectorAnimationConfig
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(
            horizontal =  8.dp,
            vertical = 8.dp
        )
    ) {
        val lastDaysText = stringResource(R.string.designSystem_dateRangeSelector_lastDays_label, dateRange.size)
        val dateRangeTitle = if (isCurrentPeriod) { lastDaysText } else { formattedDateRange }

        DateRangeSelectorHeader(
            selectedRangeTitle = dateRangeTitle,
            onPreviousPeriod = { onPeriodChanged(PeriodChangeDirection.PREVIOUS) },
            onNextPeriod = { onPeriodChanged(PeriodChangeDirection.NEXT) },
            onShowDatePicker = { showDatePicker = true },
            onToday = { onDateSelected(currentDate, dateRange) },
            canNavigateNext = canNavigateNext,
            showTodayButton = showTodayButton,
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
                        onClick = { onDateSelected(date, currentDateRange) }
                    )
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            selectedDate = selectedDate,
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
    dayNameFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEEE"),
    dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d"),
) {
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val interactionSource = remember { MutableInteractionSource() }
    val dayName = remember(date) { date.format(dayNameFormatter) }
    val dayNumber = remember(date) { date.dayOfMonth.toString() }

    val formattedDate = remember(date) { date.format(dateFormatter) }
    val selectDateDescription = stringResource(R.string.designSystem_dateRangeSelector_selectedDate_contentDescription, formattedDate)
    val futureDateDescription = stringResource(R.string.designSystem_dateRangeSelector_futureDateNotSelectable_contentDescription, formattedDate)

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
private fun Preview_DateRangeSelector_SelectedDayIsTheSameAsCurrentDay() {
    val date = LocalDate.of(2025, 1, 15)
    val state = rememberDateRangeSelectorState(
        currentDate = date,
        selectedDate = date,
        daysCount = 7
    )

    AppTheme {
        DateRangeSelector(state = state)
    }
}

@PreviewLightDark
@Composable
private fun Preview_DateRangeSelector_SelectedDayIsDifferentFromCurrentDay() {
    val date = LocalDate.of(2025, 1, 15)
    val state = rememberDateRangeSelectorState(
        currentDate = date,
        selectedDate = date.minusDays(2),
        daysCount = 7
    )

    AppTheme {
        DateRangeSelector(state = state)
    }
}

@PreviewLightDark
@Composable
private fun Preview_DateRangeSelector_SelectedDayIsTenDaysBeforeCurrentDay() {
    val date = LocalDate.of(2025, 1, 15)
    val state = rememberDateRangeSelectorState(
        currentDate = date,
        selectedDate = date.minusDays(10),
        daysCount = 7
    )

    AppTheme {
        DateRangeSelector(state = state)
    }
}