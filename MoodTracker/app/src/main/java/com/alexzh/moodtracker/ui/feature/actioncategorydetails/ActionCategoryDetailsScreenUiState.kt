package com.alexzh.moodtracker.ui.feature.actioncategorydetails

import com.alexzh.moodtracker.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.ui.model.ActionItem

data class ActionCategoryDetailsScreenUiState(
    val isLoading: Boolean = false,
    val category: ActionCategoryItem? = null,
    val actions: List<ActionItem> = emptyList()
)
