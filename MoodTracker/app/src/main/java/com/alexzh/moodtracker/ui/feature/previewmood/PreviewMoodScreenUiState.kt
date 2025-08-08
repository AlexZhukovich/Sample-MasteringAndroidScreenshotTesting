package com.alexzh.moodtracker.ui.feature.previewmood

import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import java.time.LocalDateTime

data class PreviewMoodScreenUiState(
    val isLoading: Boolean = false,
    val moodId: Long = 0,
    val mood: LocalizedMood? = null,
    val dateTime: LocalDateTime? = null,
    val actions: List<ActionItem> = emptyList(),
    val note: String = ""
)
