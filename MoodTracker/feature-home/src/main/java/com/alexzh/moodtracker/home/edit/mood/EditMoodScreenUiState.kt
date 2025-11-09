package com.alexzh.moodtracker.home.edit.mood

import android.net.Uri
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.common.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import java.time.LocalDate
import java.time.LocalTime

data class EditMoodScreenUiState(
    val isLoading: Boolean = false,
    val isNewMood: Boolean = false,
    val moodItems: SelectableMoodItems = SelectableMoodItems(),
    val actionCategoryItems: SelectableActionCategories = SelectableActionCategories(),
    val selectedDate: LocalDate,
    val selectedTime: LocalTime = LocalTime.now(),
    val note: String = "",
    val photos: List<Uri> = emptyList(),
    val isMoodDataLoaded: Boolean = false,
    val iconShape: IconShape = IconShape.CIRCLE
) {
    val canSave: Boolean
        get() = moodItems.selectedMood != null && !isLoading
}

data class SelectableMoodItems(
    val moodItems: List<LocalizedMood> = emptyList(),
    val selectedMood: LocalizedMood? = null
)

data class SelectableActionCategories(
    val userActivityCategory: Map<ActionCategoryItem, List<ActionItem>> = mapOf(),
    val selectedUserActivityIds: List<Long> = emptyList()
)