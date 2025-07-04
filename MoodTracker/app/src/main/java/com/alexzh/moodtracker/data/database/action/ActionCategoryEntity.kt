package com.alexzh.moodtracker.data.database.action

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "action_categories")
data class ActionCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val colorName: String
)