package com.alexzh.moodtracker.ui.feature.actioncategories

import com.alexzh.moodtracker.domain.PastelAccentColor

sealed class ActionCategoriesScreenEvent {
    data class OnDeleteActionCategory(val actionCategoryId: Long) : ActionCategoriesScreenEvent()
    data class OnAddActionCategory(val name: String, val color: PastelAccentColor) : ActionCategoriesScreenEvent()
    data class OnEditActionCategory(val categoryId: Long, val name: String, val color: PastelAccentColor) : ActionCategoriesScreenEvent()
}