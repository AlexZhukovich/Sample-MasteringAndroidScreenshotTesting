package com.alexzh.moodtracker.core.domain.model

data class Action(
    val id: Long,
    val title: String,
    val categoryId: Long
)