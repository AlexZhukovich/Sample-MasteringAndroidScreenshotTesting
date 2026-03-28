package com.alexzh.moodtracker.actionmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenEvent.OnAddAction
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenEvent.OnAddCategory
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenEvent.OnClearCategorySelection
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenEvent.OnDeleteAction
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenEvent.OnDeleteCategory
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenEvent.OnEditAction
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenEvent.OnEditCategory
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenEvent.OnLocaleChange
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenEvent.OnSelectCategory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.alexzh.moodtracker.core.domain.datasource.ActionCategoryDataSource
import com.alexzh.moodtracker.core.domain.datasource.ActionDataSource
import com.alexzh.moodtracker.core.domain.model.Action
import com.alexzh.moodtracker.core.domain.model.ActionCategory
import com.alexzh.moodtracker.common.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedActionCategoryNameProvider
import com.alexzh.moodtracker.common.ui.model.LocalizedActionNameProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ActionCategoriesScreenViewModel(
    private val actionCategoryNameProvider: LocalizedActionCategoryNameProvider,
    private val actionNameProvider: LocalizedActionNameProvider,
    private val actionCategoryDataSource: ActionCategoryDataSource,
    private val actionDataSource: ActionDataSource
) : ViewModel() {
    
    private val selectedCategoryId = MutableStateFlow<Long?>(null)

    private val categoriesFlow = actionCategoryDataSource.getActionCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val categoryDetailsFlow = selectedCategoryId.flatMapLatest { categoryId ->
        if (categoryId != null) {
            actionCategoryDataSource.getActionCategoryDetailsById(categoryId)
        } else {
            flowOf(null)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _uiState = MutableStateFlow(
        ActionCategoriesScreenUiState(isLoadingCategories = true)
    )

    val uiState = combine(
        categoriesFlow,
        categoryDetailsFlow,
        _uiState
    ) { categories, categoryDetails, currentUiState ->
        currentUiState.copy(
            categories = categories.map { mapToActionCategoryItem(it) },
            isLoadingCategories = false,
            selectedCategoryDetails = categoryDetails?.let {
                CategoryDetailsUiState(
                    category = ActionCategoryItem(
                        id = it.id,
                        name = actionCategoryNameProvider.getLocalizedName(it.name)
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
            is OnLocaleChange -> reloadActions()
            is OnAddCategory -> addCategory(categoryName = event.name)
            is OnEditCategory -> editCategory(categoryId = event.categoryId, categoryName = event.name)
            is OnDeleteCategory -> deleteCategory(categoryId = event.categoryId)
            is OnSelectCategory -> selectCategory(categoryId = event.categoryId)
            is OnClearCategorySelection -> clearSelection()
            is OnAddAction -> addActionToSelectedCategory(actionName = event.actionName)
            is OnEditAction -> editAction(actionId = event.actionId, actionName = event.actionName)
            is OnDeleteAction -> deleteAction(actionId = event.actionId)
        }
    }

    private fun reloadActions() {
        _uiState.update { currentState ->
            currentState.copy(
                categories = categoriesFlow.value.map { mapToActionCategoryItem(it) },
                selectedCategoryDetails = categoryDetailsFlow.value?.let {
                    CategoryDetailsUiState(
                        category = ActionCategoryItem(
                            id = it.id,
                            name = actionCategoryNameProvider.getLocalizedName(it.name)
                        ),
                        actions = it.actions.map { action -> mapToActionItem(action) },
                        isLoadingActions = false
                    )
                }
            )
        }
    }

    private fun selectCategory(categoryId: Long) {
        selectedCategoryId.value = categoryId
    }
    
    private fun clearSelection() {
        selectedCategoryId.value = null
    }

    private fun addCategory(categoryName: String) {
        viewModelScope.launch {
            val actionCategory = ActionCategory(
                id = 0,
                name = categoryName
            )
            actionCategoryDataSource.insertActionCategory(actionCategory)
        }
    }

    private fun editCategory(categoryId: Long, categoryName: String) {
        viewModelScope.launch {
            val actionCategory = ActionCategory(
                id = categoryId,
                name = categoryName
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
                name = ""
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
            name = actionCategoryNameProvider.getLocalizedName(actionCategory.name)
        )
    }

    private fun mapToActionItem(action: Action): ActionItem {
        return ActionItem(
            id = action.id,
            name = actionNameProvider.getLocalizedName(action.title)
        )
    }
}