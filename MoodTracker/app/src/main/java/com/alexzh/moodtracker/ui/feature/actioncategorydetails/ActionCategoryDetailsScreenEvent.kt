package com.alexzh.moodtracker.ui.feature.actioncategorydetails

sealed class ActionCategoryDetailsScreenEvent {
    data class OnEditAction(val actionId: Long, val actionName: String) : ActionCategoryDetailsScreenEvent()
    data class OnDeleteAction(val actionId: Long) : ActionCategoryDetailsScreenEvent()
    data class OnAddAction(val actionName: String) : ActionCategoryDetailsScreenEvent()
}
