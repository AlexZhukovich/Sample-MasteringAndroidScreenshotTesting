package com.alexzh.moodtracker.ui.feature.previewmood

sealed class PreviewMoodScreenEvent {
    data object OnDeleteMood : PreviewMoodScreenEvent()
}