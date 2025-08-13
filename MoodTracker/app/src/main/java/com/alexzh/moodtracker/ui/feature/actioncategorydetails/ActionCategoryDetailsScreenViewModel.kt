package com.alexzh.moodtracker.ui.feature.actioncategorydetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.domain.datasource.ActionCategoryDataSource
import com.alexzh.moodtracker.domain.datasource.ActionDataSource
import com.alexzh.moodtracker.domain.model.Action
import com.alexzh.moodtracker.domain.model.ActionCategoryDetails
import com.alexzh.moodtracker.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ActionCategoryDetailsScreenViewModel(
    private val actionDataSource: ActionDataSource,
    actionCategoryDataSource: ActionCategoryDataSource,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _events = Channel<UiEvent>(Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

    val actionCategoryId: Long = savedStateHandle.get<Long>("actionCategoryId") ?: -1L

    val uiState: StateFlow<ActionCategoryDetailsScreenUiState> = actionCategoryDataSource
        .getActionCategoryDetailsById(actionCategoryId)
        .map { categoryDetails ->
            if (categoryDetails == null) {
                viewModelScope.launch {
                    _events.send(UiEvent.NavigateBack)
                }
                ActionCategoryDetailsScreenUiState(isLoading = false)
            } else {
                ActionCategoryDetailsScreenUiState(
                    isLoading = false,
                    category = mapToCategoryItem(categoryDetails),
                    actions = categoryDetails.actions.map { mapToActionItem(it) }
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ActionCategoryDetailsScreenUiState(isLoading = true)
        )

    fun onEvent(event: ActionCategoryDetailsScreenEvent) {
        when (event) {
            is ActionCategoryDetailsScreenEvent.OnAddAction -> addAction(event.actionName)
            is ActionCategoryDetailsScreenEvent.OnEditAction -> editAction(event.actionId, event.actionName)
            is ActionCategoryDetailsScreenEvent.OnDeleteAction -> deleteAction(event.actionId)
        }
    }

    private fun addAction(actionName: String) {
        viewModelScope.launch {
            val action = Action(
                id = 0,
                title = actionName,
                categoryId = actionCategoryId
            )
            actionDataSource.insertAction(action)
        }
    }

    private fun editAction(actionId: Long, actionName: String) {
        viewModelScope.launch {
            val action = Action(
                id = actionId,
                title = actionName,
                categoryId = actionCategoryId
            )
            actionDataSource.updateAction(action)
        }
    }

    private fun deleteAction(actionId: Long) {
        viewModelScope.launch {
            val action = Action(
                id = actionId,
                title = "",
                categoryId = actionCategoryId
            )
            actionDataSource.deleteAction(action)
        }
    }

    private fun mapToActionItem(action: Action): ActionItem {
        return ActionItem(
            id = action.id,
            name = action.title
        )
    }

    private fun mapToCategoryItem(categoryDetails: ActionCategoryDetails): ActionCategoryItem {
        return ActionCategoryItem(
            id = categoryDetails.id,
            name = categoryDetails.name,
            color = categoryDetails.color
        )
    }
}