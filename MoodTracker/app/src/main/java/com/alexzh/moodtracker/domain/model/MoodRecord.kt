package com.alexzh.moodtracker.domain.model

import java.time.LocalDateTime

data class MoodRecord(
    val id: Long,
    val happiness: Float,
    val date: LocalDateTime,
    val note: String = ""
)