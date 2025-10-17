package com.alexzh.moodtracker.data.database

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.scale
import com.alexzh.moodtracker.domain.datasource.MoodRecordDataSource
import com.alexzh.moodtracker.domain.resolver.ImagePathResolver
import com.alexzh.moodtracker.data.database.moodphoto.MoodPhotoDao
import com.alexzh.moodtracker.data.database.moodphoto.MoodPhotoEntity
import com.alexzh.moodtracker.data.database.mood.MoodRecordEntity
import com.alexzh.moodtracker.data.database.mood.MoodRecordDao
import com.alexzh.moodtracker.data.database.moodactionrelation.MoodActionCrossRefEntity
import com.alexzh.moodtracker.data.database.moodactionrelation.MoodRecordWithActionsDao
import com.alexzh.moodtracker.domain.model.DateToHappiness
import com.alexzh.moodtracker.domain.model.MoodRecordWithActions
import com.alexzh.moodtracker.data.mapper.toDomain
import com.alexzh.moodtracker.data.mapper.toDomainList
import com.alexzh.moodtracker.domain.model.ActionToHappiness
import com.alexzh.moodtracker.domain.model.SegmentedActionImpact
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.util.UUID

class MoodRecordDataSourceImpl(
    private val context: Context,
    private val imagePathResolver: ImagePathResolver,
    private val moodRecordDao: MoodRecordDao,
    private val moodRecordWithActionsDao: MoodRecordWithActionsDao,
    private val moodPhotoDao: MoodPhotoDao
) : MoodRecordDataSource {

    override fun getMoodRecords(): Flow<List<MoodRecordWithActions>> {
        return moodRecordWithActionsDao.getMoodRecordsWithActions()
            .map { moodRecords ->
                moodRecords.map { moodRecord -> moodRecord.toDomain() }
                    .mapNotNull { it }
            }
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

    override suspend fun getSegmentedActionImpact(
        startDate: LocalDate,
        endDate: LocalDate,
        threshold: Float
    ): SegmentedActionImpact {
        val allActionData = getAverageActionToMoodHappiness(startDate, endDate)

        val positiveFactors = allActionData
            .filter { it.happiness > threshold }
            .sortedByDescending { it.happiness }

        val negativeFactors = allActionData
            .filter { it.happiness <= threshold }
            .sortedByDescending { it.happiness }

        return SegmentedActionImpact(
            positiveImpact = positiveFactors,
            negativeImpact = negativeFactors
        )
    }

    override fun getMoodRecordsForDate(date: LocalDate): Flow<List<MoodRecordWithActions>> {
        val startDateTime = date.atStartOfDay()
        val endDateTime = date.atTime(23, 59, 59)
        return moodRecordWithActionsDao.getMoodRecordsWithActionsForDates(startDateTime, endDateTime)
            .map { moodRecords ->
                moodRecords.map { moodRecord -> moodRecord.toDomain() }
                    .mapNotNull { it }
            }
    }

    override suspend fun getMoodRecordById(id: Long): MoodRecordWithActions? {
        val moodRecord = moodRecordWithActionsDao.getMoodRecordWithActionsById(id) ?: return null
        return moodRecord.toDomain()
    }

    override suspend fun insertMoodRecordWithActions(
        moodRecord: MoodRecordEntity,
        actionIds: List<Long>,
        photoUris: List<Uri>
    ): Long {
        val moodRecordId = moodRecordDao.insertMoodRecord(moodRecord)

        if (actionIds.isNotEmpty()) {
            actionIds.forEach { actionId ->
                moodRecordWithActionsDao.insertMoodActionCrossRef(
                    crossRef = MoodActionCrossRefEntity(moodRecordId, actionId)
                )
            }
        }

        if (photoUris.isNotEmpty()) {
            coroutineScope {
                photoUris.map { uri ->
                    async { savePhotoForMood(moodRecordId, uri) }
                }.awaitAll()
            }
        }

        return moodRecordId
    }

    override suspend fun updateMoodRecordWithActions(
        moodRecord: MoodRecordEntity,
        actionIds: List<Long>,
        photoUris: List<Uri>
    ) {
        moodRecordDao.updateMoodRecord(moodRecord)

        moodRecordWithActionsDao.deleteActionsForMoodRecordById(moodRecord.id)
        if (actionIds.isNotEmpty()) {
            actionIds.forEach { actionId ->
                moodRecordWithActionsDao.insertMoodActionCrossRef(
                    crossRef = MoodActionCrossRefEntity(moodRecord.id, actionId)
                )
            }
        }

        syncPhotosForMoodRecord(moodRecord.id, photoUris)
    }

    override suspend fun deleteMoodRecord(id: Long) {
        val moodRecordEntity = moodRecordWithActionsDao.getMoodRecordWithActionsById(id)?.moodRecordEntity
        if (moodRecordEntity != null) {
            deleteImagesForMood(id)
            moodRecordWithActionsDao.deleteActionsForMoodRecordById(id)
            moodRecordDao.deleteMoodRecord(moodRecordEntity)
        }
    }

    private suspend fun savePhotoForMood(moodRecordId: Long, photoUri: Uri): Result<String> {
        return try {
            val fileName = "${UUID.randomUUID()}.jpg"
            val photoFile = imagePathResolver.getImageFile(fileName)

            context.contentResolver.openInputStream(photoUri)?.use { input ->
                val bitmap = android.graphics.BitmapFactory.decodeStream(input)

                val maxDimension = 1024
                val scaleFactor = minOf(
                    maxDimension.toFloat() / bitmap.width,
                    maxDimension.toFloat() / bitmap.height,
                    1f // Don't upscale if image is smaller
                )

                val newWidth = (bitmap.width * scaleFactor).toInt()
                val newHeight = (bitmap.height * scaleFactor).toInt()

                val resizedBitmap = bitmap.scale(newWidth, newHeight)

                photoFile.outputStream().use { output ->
                    resizedBitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        85,
                        output
                    )
                }

                resizedBitmap.recycle()
                bitmap.recycle()
            }

            val photoEntity = MoodPhotoEntity(
                moodRecordId = moodRecordId,
                photoPath = fileName
            )
            moodPhotoDao.insert(photoEntity)

            Result.success(fileName)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun deleteImagesForMood(moodRecordId: Long) {
        val images = moodPhotoDao.getPhotosForMood(moodRecordId)
        images.forEach { image ->
            val file = imagePathResolver.getImageFile(image.photoPath)
            if (file.exists()) {
                file.delete()
            }
        }
        moodPhotoDao.deleteAllForMood(moodRecordId)
    }

    private suspend fun syncPhotosForMoodRecord(moodRecordId: Long, photoUris: List<Uri>) {
        val currentPhotos = moodPhotoDao.getPhotosForMood(moodRecordId)
        val currentPhotoUris = currentPhotos.map { photo ->
            imagePathResolver.resolveImageUri(photo.photoPath)
        }

        val photosToDelete = currentPhotos.filter { photo ->
            val uri = imagePathResolver.resolveImageUri(photo.photoPath)
            !photoUris.contains(uri)
        }

        val photosToAdd = photoUris.filter { uri ->
            !currentPhotoUris.contains(uri)
        }

        photosToDelete.forEach { photo ->
            val file = imagePathResolver.getImageFile(photo.photoPath)
            if (file.exists()) {
                file.delete()
            }
            moodPhotoDao.deleteById(photo.id)
        }

        if (photosToAdd.isNotEmpty()) {
            coroutineScope {
                photosToAdd.map { uri ->
                    async { savePhotoForMood(moodRecordId, uri) }
                }.awaitAll()
            }
        }
    }
}