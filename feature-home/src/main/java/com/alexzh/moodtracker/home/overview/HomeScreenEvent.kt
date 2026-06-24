package com.alexzh.moodtracker.home.overview

import java.time.LocalDate

sealed class HomeScreenEvent {
    data object OnLocaleChange : HomeScreenEvent()
    data class OnChangeData(val date: LocalDate) : HomeScreenEvent()
    data class OnSelectMoodItem(val moodId: Long) : HomeScreenEvent()
    data object OnClearSelection : HomeScreenEvent()
    data object OnDeleteMood : HomeScreenEvent()
}