package com.alexzh.moodtracker.domain.datasource

import com.alexzh.moodtracker.domain.model.ActionCategory
import com.alexzh.moodtracker.domain.model.ActionCategoryDetails
import kotlinx.coroutines.flow.Flow

interface ActionCategoryDataSource {

    fun getActionCategories(): Flow<List<ActionCategory>>

    fun getActionCategoryDetails(): Flow<List<ActionCategoryDetails>>
    
    suspend fun getActionCategoryById(id: Long): ActionCategory?

    fun getActionCategoryDetailsById(categoryId: Long): Flow<ActionCategoryDetails?>
    
    suspend fun insertActionCategory(category: ActionCategory): Long
    
    suspend fun insertActionCategories(categories: List<ActionCategory>): List<Long>
    
    suspend fun updateActionCategory(category: ActionCategory)
    
    suspend fun deleteActionCategory(category: ActionCategory)
}