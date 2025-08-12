package com.alexzh.moodtracker.ui.feature.actioncategories

import com.alexzh.moodtracker.ui.model.ActionCategoryItem

data class ActionCategoriesScreenUiState(
    val isLoading: Boolean = false,
    val categories: List<ActionCategoryItem> = emptyList()
)