package com.alexzh.moodtracker.domain.model

import com.alexzh.moodtracker.domain.PastelAccentColor

data class ActionCategory(
    val id: Long,
    val name: String,
    val color: PastelAccentColor
)