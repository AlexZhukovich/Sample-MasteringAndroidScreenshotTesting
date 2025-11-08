package com.alexzh.moodtracker.data.mapper

import com.alexzh.moodtracker.data.database.moodactionrelation.MoodRecordWithActionsEntity
import com.alexzh.moodtracker.domain.model.MoodRecordWithActions

fun MoodRecordWithActionsEntity.toDomain(): MoodRecordWithActions? {
    return MoodRecordWithActions(
        id = moodRecordEntity.id,
        happiness = moodRecordEntity.happiness,
        date = moodRecordEntity.date,
        note = moodRecordEntity.note,
        actions = this.actions.toDomainList(),
        photos = this.photos.map { it.toDomain() }
    )
}

fun List<MoodRecordWithActionsEntity>.toDomainList(): List<MoodRecordWithActions> {
    return this.mapNotNull { it.toDomain() }
}