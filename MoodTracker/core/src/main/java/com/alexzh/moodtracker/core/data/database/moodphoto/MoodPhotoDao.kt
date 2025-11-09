package com.alexzh.moodtracker.core.data.database.moodphoto

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoodPhotoDao {
    @Insert
    suspend fun insert(moodPhoto: MoodPhotoEntity): Long

    @Query("SELECT * FROM mood_photos WHERE moodRecordId = :moodRecordId ORDER BY id ASC")
    suspend fun getPhotosForMood(moodRecordId: Long): List<MoodPhotoEntity>

    @Query("DELETE FROM mood_photos WHERE id = :imageId")
    suspend fun deleteById(imageId: Long)

    @Query("DELETE FROM mood_photos WHERE moodRecordId = :moodRecordId")
    suspend fun deleteAllForMood(moodRecordId: Long)
}