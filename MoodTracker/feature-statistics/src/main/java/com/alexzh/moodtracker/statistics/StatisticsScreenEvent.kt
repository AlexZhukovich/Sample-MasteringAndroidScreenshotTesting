package com.alexzh.moodtracker.statistics

sealed class StatisticsScreenEvent {
    data object OnLocaleChange : StatisticsScreenEvent()
    data object OnPreviousMonth : StatisticsScreenEvent()
    data object OnNextMonth : StatisticsScreenEvent()
}