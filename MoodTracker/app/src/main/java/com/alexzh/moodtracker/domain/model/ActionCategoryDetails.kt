package com.alexzh.moodtracker.domain.model

data class ActionCategoryDetails(
    val category: ActionCategory,
    val actions: List<Action>
) {
    val actionsCount: Int get() = actions.size
}