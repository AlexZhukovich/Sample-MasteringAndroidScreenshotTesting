package com.alexzh.moodtracker.core.domain.model

import java.time.LocalDate

data class DateToHappiness(
    val date: LocalDate,
    val happiness: Float
)