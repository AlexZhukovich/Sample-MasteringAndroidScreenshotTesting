package com.alexzh.moodtracker.data.database.moodactionrelation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.alexzh.moodtracker.data.database.action.ActionEntity
import com.alexzh.moodtracker.data.database.mood.MoodRecordEntity
import com.alexzh.moodtracker.data.database.moodphoto.MoodPhotoEntity

data class MoodRecordWithActionsEntity(
    @Embedded val moodRecordEntity: MoodRecordEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(MoodActionCrossRefEntity::class, parentColumn = "moodRecordId", entityColumn = "actionId")
    )
    val actions: List<ActionEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "moodRecordId"
    )
    val photos: List<MoodPhotoEntity>
)