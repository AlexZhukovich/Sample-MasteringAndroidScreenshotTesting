package com.alexzh.moodtracker.data.database.mood

import androidx.room.*

@Dao
interface MoodRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoodRecord(moodRecord: MoodRecordEntry): Long

    @Update
    suspend fun updateMoodRecord(moodRecord: MoodRecordEntry)

    @Delete
    suspend fun deleteMoodRecord(moodRecord: MoodRecordEntry)
}