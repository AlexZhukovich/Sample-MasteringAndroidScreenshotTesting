package com.alexzh.moodtracker.domain.model

data class MoodRecordWithActions(
    val moodRecord: MoodRecord,
    val actions: List<Action>
)