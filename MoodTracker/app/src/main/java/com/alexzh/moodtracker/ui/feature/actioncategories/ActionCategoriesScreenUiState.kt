package com.alexzh.moodtracker.ui.feature.actioncategories

import com.alexzh.moodtracker.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.ui.model.ActionItem

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