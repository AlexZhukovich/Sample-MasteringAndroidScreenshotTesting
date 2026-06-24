package com.alexzh.moodtracker.common.ui.model

sealed class UiEvent {
    data object NavigateBack : UiEvent()
}