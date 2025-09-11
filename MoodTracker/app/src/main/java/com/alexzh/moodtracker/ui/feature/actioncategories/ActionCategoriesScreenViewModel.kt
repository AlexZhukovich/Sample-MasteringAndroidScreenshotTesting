package com.alexzh.moodtracker.ui.feature.actioncategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.alexzh.moodtracker.domain.PastelAccentColor
import com.alexzh.moodtracker.domain.datasource.ActionCategoryDataSource
import com.alexzh.moodtracker.domain.datasource.ActionDataSource
import com.alexzh.moodtracker.domain.model.Action
import com.alexzh.moodtracker.domain.model.ActionCategory
import com.alexzh.moodtracker.ui.feature.actioncategories.ActionCategoriesScreenEvent.OnAddAction
import com.alexzh.moodtracker.ui.feature.actioncategories.ActionCategoriesScreenEvent.OnAddCategory
import com.alexzh.moodtracker.ui.feature.actioncategories.ActionCategoriesScreenEvent.OnClearCategorySelection
import com.alexzh.moodtracker.ui.feature.actioncategories.ActionCategoriesScreenEvent.OnDeleteAction
import com.alexzh.moodtracker.ui.feature.actioncategories.ActionCategoriesScreenEvent.OnDeleteCategory
import com.alexzh.moodtracker.ui.feature.actioncategories.ActionCategoriesScreenEvent.OnEditAction
import com.alexzh.moodtracker.ui.feature.actioncategories.ActionCategoriesScreenEvent.OnEditCategory
import com.alexzh.moodtracker.ui.feature.actioncategories.ActionCategoriesScreenEvent.OnSelectCategory
import com.alexzh.moodtracker.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.ui.model.ActionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ActionCategoriesScreenViewModel(
    private val actionCategoryDataSource: ActionCategoryDataSource,
    private val actionDataSource: ActionDataSource
) : ViewModel() {
    
    private val selectedCategoryId = MutableStateFlow<Long?>(null)
    
    val uiState = combine(
        actionCategoryDataSource.getActionCategories(),
        selectedCategoryId.flatMapLatest { categoryId ->
            if (categoryId != null) {
                actionCategoryDataSource.getActionCategoryDetailsById(categoryId)
            } else {
                flowOf(null)
            }
        }
    ) { categories, categoryDetails ->
        ActionCategoriesScreenUiState(
            categories = categories.map { mapToActionCategoryItem(it) },
            isLoadingCategories = false,
            selectedCategoryDetails = categoryDetails?.let {
                CategoryDetailsUiState(
                    category = ActionCategoryItem(
                        id = it.id,
                        name = it.name,
                        color = it.color
                    ),
                    actions = it.actions.map { action -> mapToActionItem(action) },
                    isLoadingActions = false
                )
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ActionCategoriesScreenUiState(isLoadingCategories = true)
    )

    fun onEvent(event: ActionCategoriesScreenEvent) {
        when (event) {
            is OnAddCategory -> addCategory(categoryName = event.name, color = event.color)
            is OnEditCategory -> editCategory(categoryId = event.categoryId, categoryName = event.name, color = event.color)
            is OnDeleteCategory -> deleteCategory(categoryId = event.categoryId)
            is OnSelectCategory -> selectCategory(categoryId = event.categoryId)
            is OnClearCategorySelection -> clearSelection()
            is OnAddAction -> addActionToSelectedCategory(actionName = event.actionName)
            is OnEditAction -> editAction(actionId = event.actionId, actionName = event.actionName)
            is OnDeleteAction -> deleteAction(actionId = event.actionId)
        }
    }

    private fun selectCategory(categoryId: Long) {
        selectedCategoryId.value = categoryId
    }
    
    private fun clearSelection() {
        selectedCategoryId.value = null
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
            if (selectedCategoryId.value == categoryId) {
                selectedCategoryId.value = null
            }
            
            val actionCategory = ActionCategory(
                id = categoryId,
                name = "",
                color = PastelAccentColor.GREY
            )
            actionCategoryDataSource.deleteActionCategory(actionCategory)
        }
    }

    private fun addActionToSelectedCategory(actionName: String) {
        val categoryId = selectedCategoryId.value ?: return
        viewModelScope.launch {
            val action = Action(
                id = 0,
                title = actionName,
                categoryId = categoryId
            )
            actionDataSource.insertAction(action)
        }
    }

    private fun editAction(actionId: Long, actionName: String) {
        val categoryId = selectedCategoryId.value ?: return
        viewModelScope.launch {
            val action = Action(
                id = actionId,
                title = actionName,
                categoryId = categoryId
            )
            actionDataSource.updateAction(action)
        }
    }

    private fun deleteAction(actionId: Long) {
        val categoryId = selectedCategoryId.value ?: return
        viewModelScope.launch {
            val action = Action(
                id = actionId,
                title = "",
                categoryId = categoryId
            )
            actionDataSource.deleteAction(action)
        }
    }

    private fun mapToActionCategoryItem(actionCategory: ActionCategory): ActionCategoryItem {
        return ActionCategoryItem(
            id = actionCategory.id,
            name = actionCategory.name,
            color = actionCategory.color
        )
    }

    private fun mapToActionItem(action: Action): ActionItem {
        return ActionItem(
            id = action.id,
            name = action.title
        )
    }
}