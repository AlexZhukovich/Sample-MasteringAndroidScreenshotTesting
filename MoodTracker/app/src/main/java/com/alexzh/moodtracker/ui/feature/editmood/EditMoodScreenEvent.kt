package com.alexzh.moodtracker.ui.feature.editmood

import android.net.Uri
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import java.time.LocalDate
import java.time.LocalTime

sealed class PhotoAction {
    data class Add(val photoUri: Uri) : PhotoAction()
    data class Remove(val photoIndex: Int) : PhotoAction()
}

sealed class EditMoodScreenEvent {
    data class OnMoodChange(val mood: LocalizedMood) : EditMoodScreenEvent()
    data class OnNoteChange(val note: String) : EditMoodScreenEvent()
    data class OnActionChange(val actionItem: ActionItem) : EditMoodScreenEvent()
    data class OnDateChange(val date: LocalDate) : EditMoodScreenEvent()
    data class OnTimeChange(val time: LocalTime) : EditMoodScreenEvent()
    data class OnPhotoChange(val action: PhotoAction) : EditMoodScreenEvent()
    data object OnSave : EditMoodScreenEvent()
}