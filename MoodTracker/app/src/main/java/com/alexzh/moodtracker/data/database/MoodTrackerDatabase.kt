package com.alexzh.moodtracker.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.alexzh.moodtracker.data.database.action.ActionCategoryDao
import com.alexzh.moodtracker.data.database.action.ActionCategoryDetailsDao
import com.alexzh.moodtracker.data.database.action.ActionCategoryEntity
import com.alexzh.moodtracker.data.database.action.ActionDao
import com.alexzh.moodtracker.data.database.action.ActionEntity
import com.alexzh.moodtracker.data.database.core.Converters
import com.alexzh.moodtracker.data.database.mood.MoodRecordEntry
import com.alexzh.moodtracker.data.database.mood.MoodRecordDao
import com.alexzh.moodtracker.data.database.moodactionrelation.MoodActionCrossRef
import com.alexzh.moodtracker.data.database.moodactionrelation.MoodRecordWithActionsDao
import androidx.room.Transaction

@Database(
    entities = [
        MoodRecordEntry::class,
        ActionEntity::class,
        ActionCategoryEntity::class,
        MoodActionCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoodTrackerDatabase : RoomDatabase() {
    abstract fun moodRecordDao(): MoodRecordDao
    abstract fun moodRecordWithActionsDao(): MoodRecordWithActionsDao
    abstract fun actionDao(): ActionDao
    abstract fun actionCategoryDao(): ActionCategoryDao
    abstract fun actionCategoryDetailsDao(): ActionCategoryDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: MoodTrackerDatabase? = null

        fun getDatabase(context: Context): MoodTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodTrackerDatabase::class.java,
                    "mood_tracker_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}