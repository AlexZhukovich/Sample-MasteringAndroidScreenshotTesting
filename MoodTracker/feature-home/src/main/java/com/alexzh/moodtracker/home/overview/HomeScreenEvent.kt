package com.alexzh.moodtracker.home.overview

import java.time.LocalDate

sealed class HomeScreenEvent {
    data class OnChangeData(val date: LocalDate) : HomeScreenEvent()
    data class OnSelectMoodItem(val moodId: Long) : HomeScreenEvent()
    object OnClearSelection : HomeScreenEvent()
    object OnDeleteMood : HomeScreenEvent()
}