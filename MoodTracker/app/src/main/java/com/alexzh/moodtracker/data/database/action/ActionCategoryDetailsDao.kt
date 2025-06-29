package com.alexzh.moodtracker.data.database.action

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionCategoryDetailsDao {
    @Transaction
    @Query("SELECT * FROM action_categories ORDER BY name ASC")
    fun getActionCategoryDetails(): Flow<List<ActionCategoryDetailsEntity>>

    @Transaction
    @Query("SELECT * FROM action_categories WHERE id = :actionCategoryId")
    suspend fun getActionCategoryDetailsById(actionCategoryId: Long): ActionCategoryDetailsEntity?
}