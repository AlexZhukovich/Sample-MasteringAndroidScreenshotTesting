package com.alexzh.moodtracker.ui.feature.editmood

import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import java.time.LocalDate
import java.time.LocalTime

sealed class EditMoodScreenEvent {
    data class OnMoodChange(val mood: LocalizedMood) : EditMoodScreenEvent()
    data class OnNoteChange(val note: String) : EditMoodScreenEvent()
    data class OnActionChange(val actionItem: ActionItem) : EditMoodScreenEvent()
    data class OnDateChange(val date: LocalDate) : EditMoodScreenEvent()
    data class OnTimeChange(val time: LocalTime) : EditMoodScreenEvent()
    data object OnSave : EditMoodScreenEvent()
}