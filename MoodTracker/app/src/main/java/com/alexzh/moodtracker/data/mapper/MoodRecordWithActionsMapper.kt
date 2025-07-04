package com.alexzh.moodtracker.data.mapper

import com.alexzh.moodtracker.data.database.moodactionrelation.MoodRecordWithActionsEntity
import com.alexzh.moodtracker.domain.model.MoodRecordWithActions

fun MoodRecordWithActionsEntity.toDomain(): MoodRecordWithActions? {
    val actions = this.actions.toDomainList()

    return MoodRecordWithActions(
        id = moodRecordEntity.id,
        happiness = moodRecordEntity.happiness,
        date = moodRecordEntity.date,
        note = moodRecordEntity.note,
        actions = actions
    )
}

fun List<MoodRecordWithActionsEntity>.toDomainList(): List<MoodRecordWithActions> {
    return this.mapNotNull { it.toDomain() }
}