package com.alexzh.moodtracker.domain.model

import java.time.LocalDate

data class DateToHappiness(
    val date: LocalDate,
    val happiness: Float
)