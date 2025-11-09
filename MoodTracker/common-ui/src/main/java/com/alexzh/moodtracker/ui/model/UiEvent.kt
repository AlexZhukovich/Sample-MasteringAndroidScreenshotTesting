package com.alexzh.moodtracker.ui.model

sealed class UiEvent {
    data object NavigateBack : UiEvent()
}