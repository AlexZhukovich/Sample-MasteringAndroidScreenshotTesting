package com.alexzh.moodtracker.ui.feature.actioncategories

import com.alexzh.moodtracker.domain.PastelAccentColor

sealed class ActionCategoriesScreenEvent {
    data class OnAddCategory(val name: String, val color: PastelAccentColor) : ActionCategoriesScreenEvent()
    data class OnEditCategory(val categoryId: Long, val name: String, val color: PastelAccentColor) : ActionCategoriesScreenEvent()
    data class OnDeleteCategory(val categoryId: Long) : ActionCategoriesScreenEvent()
    data class OnSelectCategory(val categoryId: Long) : ActionCategoriesScreenEvent()
    data object OnClearCategorySelection : ActionCategoriesScreenEvent()

    data class OnAddAction(val actionName: String) : ActionCategoriesScreenEvent()
    data class OnEditAction(val actionId: Long, val actionName: String) : ActionCategoriesScreenEvent()
    data class OnDeleteAction(val actionId: Long) : ActionCategoriesScreenEvent()
}