package com.alexzh.moodtracker.core.domain.provider

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DateProvider {
    fun getCurrentDate(): LocalDate
    val currentDateFlow: Flow<LocalDate>
}