package com.alexzh.moodtracker.domain.datasource

interface SettingsDataSource {
    suspend fun isDefaultDataAdded(): Boolean
    suspend fun setDefaultDataAdded(added: Boolean)
}