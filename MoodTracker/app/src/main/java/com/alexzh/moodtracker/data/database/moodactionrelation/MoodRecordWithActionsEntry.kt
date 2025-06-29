package com.alexzh.moodtracker.data.database.moodactionrelation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.alexzh.moodtracker.data.database.action.ActionEntity
import com.alexzh.moodtracker.data.database.mood.MoodRecordEntry

data class MoodRecordWithActionsEntry(
    @Embedded val moodRecordEntry: MoodRecordEntry,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(MoodActionCrossRef::class, parentColumn = "moodRecordEntryId", entityColumn = "actionId")
    )
    val actions: List<ActionEntity>
)