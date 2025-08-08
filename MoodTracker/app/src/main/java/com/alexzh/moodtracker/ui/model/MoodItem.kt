package com.alexzh.moodtracker.ui.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MoodItem(
    val id: Long,
    val mood: LocalizedMood,
    val date: LocalDateTime,
    val note: String,
    val actions: List<ActionItem>
) {
    val formattedDate: String
        get() = date.format(DateTimeFormatter.ofPattern("HH:mm"))
}