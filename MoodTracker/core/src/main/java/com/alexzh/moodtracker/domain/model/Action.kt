package com.alexzh.moodtracker.domain.model

data class Action(
    val id: Long,
    val title: String,
    val categoryId: Long
)