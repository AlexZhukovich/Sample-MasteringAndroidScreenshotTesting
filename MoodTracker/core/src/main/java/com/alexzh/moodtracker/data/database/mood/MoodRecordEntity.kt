package com.alexzh.moodtracker.data.database.mood

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "mood_records")
data class MoodRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val happiness: Float,
    val date: LocalDateTime,
    val note: String = ""
)