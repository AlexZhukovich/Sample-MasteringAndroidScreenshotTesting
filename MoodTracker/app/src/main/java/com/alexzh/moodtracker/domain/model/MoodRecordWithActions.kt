package com.alexzh.moodtracker.domain.model

import java.time.LocalDateTime

data class MoodRecordWithActions(
    val id: Long,
    val happiness: Float,
    val date: LocalDateTime,
    val note: String,
    val actions: List<Action>
)