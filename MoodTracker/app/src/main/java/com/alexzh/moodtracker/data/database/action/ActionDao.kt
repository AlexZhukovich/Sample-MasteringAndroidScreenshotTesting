package com.alexzh.moodtracker.data.database.action

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionDao {
    @Query("SELECT * FROM actions ORDER BY title ASC")
    fun getActions(): Flow<List<ActionEntity>>

    @Query("SELECT * FROM actions WHERE id = :actionId")
    suspend fun getActionById(actionId: Long): ActionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAction(action: ActionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActions(actions: List<ActionEntity>): List<Long>

    @Update
    suspend fun updateAction(action: ActionEntity)

    @Delete
    suspend fun deleteAction(action: ActionEntity)
}