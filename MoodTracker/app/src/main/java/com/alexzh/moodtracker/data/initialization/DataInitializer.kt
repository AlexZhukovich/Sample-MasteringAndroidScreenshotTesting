package com.alexzh.moodtracker.data.initialization

import com.alexzh.moodtracker.domain.datasource.ActionCategoryDataSource
import com.alexzh.moodtracker.domain.datasource.ActionDataSource
import com.alexzh.moodtracker.domain.datasource.SettingsDataSource

class DataInitializer(
    private val settingsDataSource: SettingsDataSource,
    private val actionCategoryDataSource: ActionCategoryDataSource,
    private val actionDataSource: ActionDataSource
) {
    
    suspend fun initializeDefaultData() {
        try {
            if (!settingsDataSource.isDefaultDataAdded()) {
                insertDefaultData()
                settingsDataSource.setDefaultDataAdded(true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private suspend fun insertDefaultData() {
        actionCategoryDataSource.insertActionCategories(DefaultData.defaultCategories)
        actionDataSource.insertActions(DefaultData.defaultActions)
    }
}