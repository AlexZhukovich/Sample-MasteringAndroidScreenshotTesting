package com.alexzh.moodtracker.domain.model

import com.alexzh.moodtracker.domain.PastelAccentColor

data class ActionCategoryDetails(
    val id: Long,
    val name: String,
    val color: PastelAccentColor,
    val actions: List<Action>
) {
    val actionsCount: Int get() = actions.size
}