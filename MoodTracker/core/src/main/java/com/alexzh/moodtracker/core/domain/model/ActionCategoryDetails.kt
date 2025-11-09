package com.alexzh.moodtracker.core.domain.model

data class ActionCategoryDetails(
    val id: Long,
    val name: String,
    val actions: List<Action>
)