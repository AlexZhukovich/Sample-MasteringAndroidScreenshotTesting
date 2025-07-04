package com.alexzh.moodtracker.data.database.moodactionrelation

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "mood_action_cross_ref",
    primaryKeys = ["moodRecordId", "actionId"],
    indices = [Index(value = ["actionId"])]
)
data class MoodActionCrossRefEntity(
    val moodRecordId: Long,
    val actionId: Long
)