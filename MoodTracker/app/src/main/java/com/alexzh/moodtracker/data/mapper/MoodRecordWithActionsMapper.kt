package com.alexzh.moodtracker.data.mapper

import com.alexzh.moodtracker.data.database.moodactionrelation.MoodRecordWithActionsEntity
import com.alexzh.moodtracker.domain.model.MoodRecordWithActions

fun MoodRecordWithActionsEntity.toDomain(): MoodRecordWithActions? {
    val moodRecord = this.moodRecordEntity.toDomain() ?: return null
    val actions = this.actions.toDomainList()

    return MoodRecordWithActions(
        moodRecord = moodRecord,
        actions = actions
    )
}

fun List<MoodRecordWithActionsEntity>.toDomainList(): List<MoodRecordWithActions> {
    return this.mapNotNull { it.toDomain() }
}