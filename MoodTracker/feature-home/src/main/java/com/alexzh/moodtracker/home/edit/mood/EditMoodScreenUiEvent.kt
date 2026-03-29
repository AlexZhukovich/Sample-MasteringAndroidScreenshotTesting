package com.alexzh.moodtracker.home.edit.mood

sealed class EditMoodScreenUiEvent {
    data object SaveSuccess : EditMoodScreenUiEvent()
}