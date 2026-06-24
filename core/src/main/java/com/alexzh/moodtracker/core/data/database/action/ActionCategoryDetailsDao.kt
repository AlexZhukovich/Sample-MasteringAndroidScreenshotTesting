package com.alexzh.moodtracker.core.data.database.action

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionCategoryDetailsDao {
    @Transaction
    @Query("SELECT * FROM action_categories ORDER BY name ASC")
    fun getActionCategoryDetails(): Flow<List<ActionCategoryDetailsEntity>>

    @Transaction
    @Query("SELECT * FROM action_categories WHERE id = :actionCategoryId")
    fun getActionCategoryDetailsByIdFlow(actionCategoryId: Long): Flow<ActionCategoryDetailsEntity?>
}