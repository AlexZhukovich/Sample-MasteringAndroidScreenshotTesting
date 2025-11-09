package com.alexzh.moodtracker.core.domain.datasource

import com.alexzh.moodtracker.core.domain.model.IconShape
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {
    suspend fun isDefaultDataAdded(): Boolean
    suspend fun setDefaultDataAdded(added: Boolean)
    fun isDynamicColorsEnabled(): Flow<Boolean>
    suspend fun setDynamicColorsEnabled(enabled: Boolean)
    fun getIconShape(default: IconShape = IconShape.CIRCLE): Flow<IconShape>
    suspend fun setIconShape(iconShape: IconShape)
}