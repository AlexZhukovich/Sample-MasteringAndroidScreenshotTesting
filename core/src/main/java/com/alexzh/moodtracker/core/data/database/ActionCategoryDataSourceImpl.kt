package com.alexzh.moodtracker.core.data.database

import com.alexzh.moodtracker.core.data.mapper.toDomain
import com.alexzh.moodtracker.core.data.mapper.toDomainList
import com.alexzh.moodtracker.core.data.mapper.toEntity
import com.alexzh.moodtracker.core.data.mapper.toEntityList
import com.alexzh.moodtracker.core.domain.datasource.ActionCategoryDataSource
import com.alexzh.moodtracker.core.data.database.action.ActionCategoryDao
import com.alexzh.moodtracker.core.data.database.action.ActionCategoryDetailsDao
import com.alexzh.moodtracker.core.domain.model.ActionCategory
import com.alexzh.moodtracker.core.domain.model.ActionCategoryDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActionCategoryDataSourceImpl(
    private val actionCategoryDao: ActionCategoryDao,
    private val actionCategoryDetailsDao: ActionCategoryDetailsDao
) : ActionCategoryDataSource {

    override fun getActionCategories(): Flow<List<ActionCategory>> {
        return actionCategoryDao.getActionCategories()
            .map { it.toDomainList() }
    }

    override fun getActionCategoryDetails(): Flow<List<ActionCategoryDetails>> {
        return actionCategoryDetailsDao.getActionCategoryDetails()
            .map { it.toDomainList() }
    }

    override suspend fun getActionCategoryById(id: Long): ActionCategory? {
        return actionCategoryDao.getActionCategoryById(id)
            ?.toDomain()
    }

    override fun getActionCategoryDetailsById(categoryId: Long): Flow<ActionCategoryDetails?> {
        return actionCategoryDetailsDao.getActionCategoryDetailsByIdFlow(categoryId)
            .map { it?.toDomain() }
    }

    override suspend fun insertActionCategory(category: ActionCategory): Long {
        return actionCategoryDao.insertActionCategory(
            actionCategory = category.toEntity()
        )
    }

    override suspend fun insertActionCategories(categories: List<ActionCategory>): List<Long> {
        return actionCategoryDao.insertActionCategories(
            actionCategories = categories.toEntityList()
        )
    }

    override suspend fun updateActionCategory(category: ActionCategory) {
        actionCategoryDao.updateActionCategory(
            actionCategory = category.toEntity()
        )
    }

    override suspend fun deleteActionCategory(category: ActionCategory) {
        actionCategoryDao.deleteActionCategory(
            actionCategory = category.toEntity()
        )
    }
}