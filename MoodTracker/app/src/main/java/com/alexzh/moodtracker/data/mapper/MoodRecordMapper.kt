package com.alexzh.moodtracker.data.mapper

import com.alexzh.moodtracker.data.database.mood.MoodRecordEntity
import com.alexzh.moodtracker.domain.model.MoodRecord

fun MoodRecordEntity.toDomain(): MoodRecord {
    return MoodRecord(
        id = this.id,
        happiness = this.happiness,
        date = this.date,
        note = this.note
    )
}

fun MoodRecord.toEntity(): MoodRecordEntity {
    return MoodRecordEntity(
        id = this.id,
        happiness = this.happiness,
        date = this.date,
        note = this.note
    )
}

fun List<MoodRecordEntity>.toDomainList(): List<MoodRecord> {
    return this.map { it.toDomain() }
}