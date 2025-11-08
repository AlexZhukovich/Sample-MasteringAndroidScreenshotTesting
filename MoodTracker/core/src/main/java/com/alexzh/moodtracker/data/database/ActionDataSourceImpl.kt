package com.alexzh.moodtracker.data.database

import com.alexzh.moodtracker.data.mapper.toDomain
import com.alexzh.moodtracker.data.mapper.toDomainList
import com.alexzh.moodtracker.data.mapper.toEntity
import com.alexzh.moodtracker.data.mapper.toEntityList
import com.alexzh.moodtracker.domain.datasource.ActionDataSource
import com.alexzh.moodtracker.data.database.action.ActionDao
import com.alexzh.moodtracker.domain.model.Action
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActionDataSourceImpl(
    private val actionDao: ActionDao
) : ActionDataSource {

    override fun getActions(): Flow<List<Action>> {
        return actionDao.getActions()
            .map { it.toDomainList() }
    }

    override suspend fun getActionById(id: Long): Action? {
        return actionDao.getActionById(id)
            ?.toDomain()
    }

    override suspend fun insertAction(action: Action): Long {
        return actionDao.insertAction(
            action = action.toEntity()
        )
    }

    override suspend fun insertActions(actions: List<Action>): List<Long> {
        return actionDao.insertActions(
            actions = actions.toEntityList()
        )
    }

    override suspend fun updateAction(action: Action) {
        actionDao.updateAction(
            action = action.toEntity()
        )
    }

    override suspend fun deleteAction(action: Action) {
        actionDao.deleteAction(
            action = action.toEntity()
        )
    }
}