package com.alexzh.moodtracker.domain.datasource

import com.alexzh.moodtracker.domain.model.Action
import kotlinx.coroutines.flow.Flow

interface ActionDataSource {
    fun getActions(): Flow<List<Action>>

    suspend fun getActionById(id: Long): Action?
    
    suspend fun insertAction(action: Action): Long
    
    suspend fun insertActions(actions: List<Action>): List<Long>
    
    suspend fun updateAction(action: Action)
    
    suspend fun deleteAction(action: Action)
}