package com.alexzh.moodtracker.actionmanagement

import com.alexzh.moodtracker.common.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.common.ui.model.ActionItem

data class ActionCategoriesScreenUiState(
    val categories: List<ActionCategoryItem> = emptyList(),
    val isLoadingCategories: Boolean = false,
    val selectedCategoryDetails: CategoryDetailsUiState? = null
)

data class CategoryDetailsUiState(
    val category: ActionCategoryItem,
    val actions: List<ActionItem> = emptyList(),
    val isLoadingActions: Boolean = false
)