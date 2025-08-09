package com.alexzh.moodtracker.data.database

import com.alexzh.moodtracker.domain.datasource.MoodRecordDataSource
import com.alexzh.moodtracker.data.database.mood.MoodRecordEntity
import com.alexzh.moodtracker.data.database.mood.MoodRecordDao
import com.alexzh.moodtracker.data.database.moodactionrelation.MoodActionCrossRefEntity
import com.alexzh.moodtracker.data.database.moodactionrelation.MoodRecordWithActionsDao
import com.alexzh.moodtracker.domain.model.DateToHappiness
import com.alexzh.moodtracker.domain.model.MoodRecordWithActions
import com.alexzh.moodtracker.data.mapper.toDomain
import com.alexzh.moodtracker.data.mapper.toDomainList
import com.alexzh.moodtracker.domain.model.ActionToHappiness
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class MoodRecordDataSourceImpl(
    private val moodRecordDao: MoodRecordDao,
    private val moodRecordWithActionsDao: MoodRecordWithActionsDao,
) : MoodRecordDataSource {

    override fun getMoodRecords(): Flow<List<MoodRecordWithActions>> {
        return moodRecordWithActionsDao.getMoodRecordsWithActions()
            .map { it.toDomainList() }
    }

    override suspend fun getAverageDayToMoodHappiness(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<DateToHappiness> {
        val startDateTime = startDate.atStartOfDay()
        val endDateTime = endDate.atTime(23, 59, 59)
        
        val moodRecordEntities = moodRecordWithActionsDao
            .getMoodRecordsWithActionsForDates(startDateTime, endDateTime)
            .first()
            .toDomainList()

        return moodRecordEntities
            .groupBy { it.date.toLocalDate() }
            .map { (date, entitiesForDate) ->
                val averageHappiness = entitiesForDate
                    .map { it.happiness }
                    .average()
                    .toFloat()
                DateToHappiness(date, averageHappiness)
            }
            .sortedBy { it.date }
    }

    override suspend fun getAverageActionToMoodHappiness(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<ActionToHappiness> {
        val startDateTime = startDate.atStartOfDay()
        val endDateTime = endDate.atTime(23, 59, 59)
        
        val moodRecordEntities = moodRecordWithActionsDao
            .getMoodRecordsWithActionsForDates(startDateTime, endDateTime)
            .first()
            .toDomainList()

        return moodRecordEntities
            .flatMap { moodRecord ->
                moodRecord.actions.map { action ->
                    action.title to moodRecord.happiness
                }
            }
            .groupBy { it.first }
            .map { (actionTitle, actionHappinessPairs) ->
                val averageHappiness = actionHappinessPairs
                    .map { it.second }
                    .average()
                    .toFloat()
                ActionToHappiness(actionTitle, averageHappiness)
            }
            .sortedByDescending { it.happiness }
    }

    override fun getMoodRecordsForDate(date: LocalDate): Flow<List<MoodRecordWithActions>> {
        val startDateTime = date.atStartOfDay()
        val endDateTime = date.atTime(23, 59, 59)
        return moodRecordWithActionsDao.getMoodRecordsWithActionsForDates(startDateTime, endDateTime)
            .map { it.toDomainList() }
    }

    override suspend fun getMoodRecordById(id: Long): MoodRecordWithActions? {
        return moodRecordWithActionsDao.getMoodRecordWithActionsById(id)?.toDomain()
    }

    override suspend fun insertMoodRecordWithActions(moodRecord: MoodRecordEntity, actionIds: List<Long>): Long {
        val moodRecordId = moodRecordDao.insertMoodRecord(moodRecord)
        if (actionIds.isNotEmpty()) {
            actionIds.forEach { actionId ->
                moodRecordWithActionsDao.insertMoodActionCrossRef(
                    crossRef = MoodActionCrossRefEntity(moodRecordId, actionId)
                )
            }
        }
        return moodRecordId
    }

    override suspend fun updateMoodRecordWithActions(moodRecord: MoodRecordEntity, actionIds: List<Long>) {
        moodRecordDao.updateMoodRecord(moodRecord)
        moodRecordWithActionsDao.deleteActionsForMoodRecordById(moodRecord.id)
        if (actionIds.isNotEmpty()) {
            actionIds.forEach { actionId ->
                moodRecordWithActionsDao.insertMoodActionCrossRef(
                    crossRef = MoodActionCrossRefEntity(moodRecord.id, actionId)
                )
            }
        }
    }

    override suspend fun deleteMoodRecord(id: Long) {
        val moodRecordEntity = moodRecordWithActionsDao.getMoodRecordWithActionsById(id)?.moodRecordEntity
        if (moodRecordEntity != null) {
            moodRecordWithActionsDao.deleteActionsForMoodRecordById(id)
            moodRecordDao.deleteMoodRecord(moodRecordEntity)
        }
    }
}