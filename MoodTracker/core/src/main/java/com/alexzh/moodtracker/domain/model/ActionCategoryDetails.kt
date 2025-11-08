package com.alexzh.moodtracker.domain.model

data class ActionCategoryDetails(
    val id: Long,
    val name: String,
    val actions: List<Action>
)