package com.alexzh.moodtracker.data.database.moodactionrelation

import androidx.room.*
import com.alexzh.moodtracker.data.database.mood.MoodRecordEntry
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface MoodRecordWithActionsDao {
    @Transaction
    @Query("SELECT * FROM mood_records ORDER BY date DESC")
    fun getMoodRecordsWithActions(): Flow<List<MoodRecordWithActionsEntry>>

    @Transaction
    @Query("SELECT * FROM mood_records WHERE id = :moodRecordId")
    suspend fun getMoodRecordWithActionsById(moodRecordId: Long): MoodRecordWithActionsEntry?

    @Transaction
    @Query("SELECT * FROM mood_records WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getMoodRecordsWithActionsForDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<MoodRecordWithActionsEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoodActionCrossRef(crossRef: MoodActionCrossRef)

    @Delete
    suspend fun deleteMoodActionCrossRef(crossRef: MoodActionCrossRef)

    @Query("DELETE FROM mood_action_cross_ref WHERE moodRecordEntryId = :moodRecordId")
    suspend fun deleteActionsForMoodRecordById(moodRecordId: Long)
}