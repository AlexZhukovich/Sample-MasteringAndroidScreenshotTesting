package com.alexzh.moodtracker.ui.feature.actioncategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.domain.PastelAccentColor
import com.alexzh.moodtracker.domain.datasource.ActionCategoryDataSource
import com.alexzh.moodtracker.domain.model.ActionCategory
import com.alexzh.moodtracker.ui.model.ActionCategoryItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActionCategoriesScreenViewModel(
    private val actionCategoryDataSource: ActionCategoryDataSource
): ViewModel() {

    private val categoriesFlow = actionCategoryDataSource.getActionCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val uiState: StateFlow<ActionCategoriesScreenUiState> = categoriesFlow
        .map { categories ->
            ActionCategoriesScreenUiState(
                isLoading = false,
                categories = categories.map { mapToActionCategoryItem(it) }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ActionCategoriesScreenUiState(isLoading = true)
        )

    fun onEvent(event: ActionCategoriesScreenEvent) {
        when (event) {
            is ActionCategoriesScreenEvent.OnAddActionCategory -> addCategory(event.name, event.color)
            is ActionCategoriesScreenEvent.OnEditActionCategory -> editCategory(event.categoryId, event.name, event.color)
            is ActionCategoriesScreenEvent.OnDeleteActionCategory -> deleteCategory(event.actionCategoryId)
        }
    }

    private fun addCategory(categoryName: String, color: PastelAccentColor) {
        viewModelScope.launch {
            val actionCategory = ActionCategory(
                id = 0,
                name = categoryName,
                color = color
            )
            actionCategoryDataSource.insertActionCategory(actionCategory)
        }
    }

    private fun editCategory(categoryId: Long, categoryName: String, color: PastelAccentColor) {
        viewModelScope.launch {
            val actionCategory = ActionCategory(
                id = categoryId,
                name = categoryName,
                color = color
            )
            actionCategoryDataSource.updateActionCategory(actionCategory)
        }
    }

    private fun deleteCategory(categoryId: Long) {
        viewModelScope.launch {
            val actionCategory = ActionCategory(
                id = categoryId,
                name = "",
                color = PastelAccentColor.GREY
            )
            actionCategoryDataSource.deleteActionCategory(actionCategory)
        }
    }

    private fun mapToActionCategoryItem(actionCategory: ActionCategory): ActionCategoryItem {
        return ActionCategoryItem(
            id = actionCategory.id,
            name = actionCategory.name,
            color = actionCategory.color
        )
    }
}