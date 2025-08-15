package com.alexzh.moodtracker.domain.datasource

import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {
    suspend fun isDefaultDataAdded(): Boolean
    suspend fun setDefaultDataAdded(added: Boolean)
    fun isDynamicColorsEnabled(): Flow<Boolean>
    suspend fun setDynamicColorsEnabled(enabled: Boolean)
}