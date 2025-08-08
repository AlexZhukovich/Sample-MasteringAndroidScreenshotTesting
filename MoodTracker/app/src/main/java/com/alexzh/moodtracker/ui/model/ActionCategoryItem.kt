package com.alexzh.moodtracker.ui.model

import com.alexzh.moodtracker.domain.PastelAccentColor

data class ActionCategoryItem(
    val id: Long,
    val name: String,
    val color: PastelAccentColor
)