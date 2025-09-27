package com.alexzh.moodtracker.domain.datasource

import com.alexzh.moodtracker.data.database.mood.MoodRecordEntity
import com.alexzh.moodtracker.domain.model.ActionToHappiness
import com.alexzh.moodtracker.domain.model.DateToHappiness
import com.alexzh.moodtracker.domain.model.MoodRecordWithActions
import com.alexzh.moodtracker.domain.model.SegmentedActionImpact
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MoodRecordDataSource {
    fun getMoodRecords(): Flow<List<MoodRecordWithActions>>

    suspend fun getAverageDayToMoodHappiness(startDate: LocalDate, endDate: LocalDate): List<DateToHappiness>

    suspend fun getAverageActionToMoodHappiness(startDate: LocalDate, endDate: LocalDate): List<ActionToHappiness>

    suspend fun getSegmentedActionImpact(startDate: LocalDate, endDate: LocalDate, threshold: Float = 3.4f): SegmentedActionImpact

    fun getMoodRecordsForDate(date: LocalDate): Flow<List<MoodRecordWithActions>>
    
    suspend fun getMoodRecordById(id: Long): MoodRecordWithActions?
    
    suspend fun insertMoodRecordWithActions(moodRecord: MoodRecordEntity, actionIds: List<Long>): Long
    
    suspend fun updateMoodRecordWithActions(moodRecord: MoodRecordEntity, actionIds: List<Long>)
    
    suspend fun deleteMoodRecord(id: Long)
}