package com.alexzh.moodtracker.data.database.mood

import androidx.room.*

@Dao
interface MoodRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoodRecord(moodRecord: MoodRecordEntity): Long

    @Update
    suspend fun updateMoodRecord(moodRecord: MoodRecordEntity)

    @Delete
    suspend fun deleteMoodRecord(moodRecord: MoodRecordEntity)
}