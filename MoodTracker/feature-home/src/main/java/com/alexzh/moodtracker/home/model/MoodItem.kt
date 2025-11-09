package com.alexzh.moodtracker.home.model

import android.net.Uri
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MoodItem(
    val id: Long,
    val mood: LocalizedMood,
    val date: LocalDateTime,
    val note: String,
    val actions: List<ActionItem>,
    val photos: List<Uri> = emptyList()
) {
    val formattedDate: String
        get() = date.format(DateTimeFormatter.ofPattern("HH:mm"))
}