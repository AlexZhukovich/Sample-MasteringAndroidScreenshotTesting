package com.alexzh.designsystem.component.selector.daterangeselector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun rememberDateRangeSelectorState(
    daysCount: Int = 7,
    selectedDate: LocalDate = LocalDate.now(),
    currentDate: LocalDate = LocalDate.now(),
    formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d")
): DateRangeSelectorState {
    val state = remember(daysCount) {
        DateRangeSelectorState(
            initialSelectedDate = selectedDate,
            daysCount = daysCount,
            initialCurrentDate = currentDate,
            formatter = formatter
        )
    }

    LaunchedEffect(currentDate) {
        state.updateCurrentDate(currentDate)
    }

    LaunchedEffect(selectedDate) {
        state.updateSelectedDate(selectedDate)
    }

    return state
}

@Stable
class DateRangeSelectorState(
    initialSelectedDate: LocalDate,
    private val daysCount: Int,
    initialCurrentDate: LocalDate = LocalDate.now(),
    formatter: DateTimeFormatter
) {
    private var _rangeStartDate by mutableStateOf(calculateRangeStartForDate(initialSelectedDate, daysCount, initialCurrentDate))
    private var _currentDate by mutableStateOf(initialCurrentDate)
    private var _selectedDate by mutableStateOf(initialSelectedDate)
    private var _navigationDirection by mutableStateOf(PeriodChangeDirection.NONE)

    val currentDate: LocalDate by derivedStateOf { _currentDate }
    val selectedDate: LocalDate
        get() = _selectedDate
    val navigationDirection: PeriodChangeDirection
        get() = _navigationDirection
    val dateRange: List<LocalDate> by derivedStateOf {
        generateDateRange(_rangeStartDate, daysCount)
    }
    val showTodayButton: Boolean by derivedStateOf {
        _selectedDate != currentDate
    }
    val canNavigateNext: Boolean by derivedStateOf {
        _rangeStartDate.plusDays(daysCount.toLong()) <= currentDate
    }

    fun updateCurrentDate(newDate: LocalDate) {
        val oldCurrentDate = _currentDate
        _currentDate = newDate
        
        if (_selectedDate == oldCurrentDate && oldCurrentDate != newDate) {
            _selectedDate = newDate
            val newRangeStart = calculateRangeStartForDate(newDate, daysCount, newDate)
            if (_rangeStartDate != newRangeStart) {
                _rangeStartDate = newRangeStart
            }
        }
    }

    fun updateSelectedDate(newSelectedDate: LocalDate) {
        if (_selectedDate != newSelectedDate) {
            _selectedDate = newSelectedDate
            val newRangeStart = calculateRangeStartForDate(newSelectedDate, daysCount, currentDate)
            if (_rangeStartDate != newRangeStart) {
                _rangeStartDate = newRangeStart
            }
        }
    }

    val formattedDateRange: String by derivedStateOf {
        val range = dateRange
        val startDate = range.first()
        val endDate = range.last()
        val endDateFormatted = endDate.format(formatter)
        val startDateFormatted = startDate.format(formatter)
        "$startDateFormatted - $endDateFormatted"
    }

    val isCurrentPeriod: Boolean by derivedStateOf {
        val range = dateRange
        range.lastOrNull() == currentDate
    }

    fun onDateSelected(date: LocalDate, displayedRange: List<LocalDate>? = null) {
        val rangeToCheck = displayedRange ?: dateRange

        if (rangeToCheck.contains(date)) {
            _selectedDate = date
        } else {
            val previousRangeStart = _rangeStartDate
            val newRangeStart = calculateRangeStartForDate(date, daysCount, currentDate)
            _rangeStartDate = newRangeStart
            _selectedDate = date
            _navigationDirection = if (newRangeStart.isBefore(previousRangeStart)) {
                PeriodChangeDirection.PREVIOUS
            } else {
                PeriodChangeDirection.NEXT
            }
        }
    }

    private fun onPreviousPeriod() {
        _rangeStartDate = _rangeStartDate.minusDays(daysCount.toLong())
        _navigationDirection = PeriodChangeDirection.PREVIOUS

        val newRange = generateDateRange(_rangeStartDate, daysCount)
        if (!newRange.contains(_selectedDate)) {
            _selectedDate = newRange.last()
        }
    }

    private fun onNextPeriod() {
        val nextRangeStart = _rangeStartDate.plusDays(daysCount.toLong())
        if (nextRangeStart.plusDays(daysCount.toLong() - 1) <= currentDate) {
            _rangeStartDate = nextRangeStart
            _navigationDirection = PeriodChangeDirection.NEXT

            val newRange = generateDateRange(_rangeStartDate, daysCount)
            if (!newRange.contains(_selectedDate)) {
                _selectedDate = newRange.last()
            }
        }
    }

    fun onPeriodChanged(direction: PeriodChangeDirection, coroutineScope: CoroutineScope) {
        when (direction) {
            PeriodChangeDirection.PREVIOUS -> coroutineScope.launch { onPreviousPeriod() }
            PeriodChangeDirection.NEXT -> coroutineScope.launch { onNextPeriod() }
            PeriodChangeDirection.NONE -> { /* No action needed */ }
        }
    }

    fun resetNavigationDirection() {
        _navigationDirection = PeriodChangeDirection.NONE
    }

    private fun generateDateRange(startDate: LocalDate, daysCount: Int): List<LocalDate> {
        return (0 until daysCount).map { dayOffset ->
            startDate.plusDays(dayOffset.toLong())
        }
    }

    private fun calculateRangeStartForDate(targetDate: LocalDate, daysCount: Int, currentDate: LocalDate): LocalDate {
        if (targetDate >= currentDate) {
            return currentDate.minusDays(daysCount.toLong() - 1)
        }

        val currentPeriodStart = currentDate.minusDays(daysCount.toLong() - 1)
        val daysBefore = currentPeriodStart.until(targetDate, ChronoUnit.DAYS)
        if (daysBefore >= 0) {
            return currentPeriodStart
        }

        val periodsBack = (-daysBefore - 1) / daysCount + 1
        return currentPeriodStart.minusDays(periodsBack * daysCount)
    }
}