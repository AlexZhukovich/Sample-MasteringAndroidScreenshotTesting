package com.alexzh.moodtracker.data.database.moodphoto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.alexzh.moodtracker.data.database.mood.MoodRecordEntity

@Entity(
    tableName = "mood_photos",
    foreignKeys = [
        ForeignKey(
            entity = MoodRecordEntity::class,
            parentColumns = ["id"],
            childColumns = ["moodRecordId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["moodRecordId"])]
)
data class MoodPhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val moodRecordId: Long,
    val photoPath: String
)