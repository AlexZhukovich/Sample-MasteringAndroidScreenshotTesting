package com.alexzh.moodtracker.data.database.action

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionCategoryDao {
    @Query("SELECT * FROM action_categories")
    fun getActionCategories(): Flow<List<ActionCategoryEntity>>

    @Query("SELECT * FROM action_categories WHERE id = :actionCategoryId")
    suspend fun getActionCategoryById(actionCategoryId: Long): ActionCategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActionCategory(actionCategory: ActionCategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActionCategories(actionCategories: List<ActionCategoryEntity>): List<Long>

    @Update
    suspend fun updateActionCategory(actionCategory: ActionCategoryEntity)

    @Delete
    suspend fun deleteActionCategory(actionCategory: ActionCategoryEntity)
}