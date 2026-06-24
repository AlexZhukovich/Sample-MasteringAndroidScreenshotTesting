package com.alexzh.moodtracker.core.data.database.action

import androidx.room.Embedded
import androidx.room.Relation

data class ActionCategoryDetailsEntity(
    @Embedded val category: ActionCategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val actions: List<ActionEntity>
)