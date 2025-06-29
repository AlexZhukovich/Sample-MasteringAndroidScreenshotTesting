package com.alexzh.moodtracker.data.database.moodactionrelation

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "mood_action_cross_ref",
    primaryKeys = ["moodRecordEntryId", "actionId"],
    indices = [Index(value = ["actionId"])]
)
data class MoodActionCrossRef(
    val moodRecordEntryId: Long,
    val actionId: Long
)