package com.alexzh.moodtracker.ui.feature.statistics

sealed class StatisticsScreenEvent {
    data object OnPreviousMonth : StatisticsScreenEvent()
    data object OnNextMonth : StatisticsScreenEvent()
}