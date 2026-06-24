package com.alexzh.moodtracker.core.data.database.moodactionrelation

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface MoodRecordWithActionsDao {
    @Transaction
    @Query("SELECT * FROM mood_records ORDER BY date DESC")
    fun getMoodRecordsWithActions(): Flow<List<MoodRecordWithActionsEntity>>

    @Transaction
    @Query("SELECT * FROM mood_records WHERE id = :moodRecordId")
    suspend fun getMoodRecordWithActionsById(moodRecordId: Long): MoodRecordWithActionsEntity?

    @Transaction
    @Query("SELECT * FROM mood_records WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getMoodRecordsWithActionsForDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<MoodRecordWithActionsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoodActionCrossRef(crossRef: MoodActionCrossRefEntity)

    @Delete
    suspend fun deleteMoodActionCrossRef(crossRef: MoodActionCrossRefEntity)

    @Query("DELETE FROM mood_action_cross_ref WHERE moodRecordId = :moodRecordId")
    suspend fun deleteActionsForMoodRecordById(moodRecordId: Long)
}