package com.alexzh.moodtracker.common.ui.model

import android.content.Context
import com.alexzh.moodtracker.common.ui.R
import com.alexzh.moodtracker.core.data.initialization.DefaultData

class LocalizedActionCategoryNameProvider(
    private val context: Context
) {
    fun getLocalizedName(name: String): String {
        val resId = nameToStringRes(name)
        return if (resId != null) context.getString(resId) else name
    }

    private fun nameToStringRes(name: String): Int? = when (name) {
        DefaultData.CATEGORY_PHYSICAL_ACTIVITY_LABEL -> R.string.actionCategory_physicalActivity_label
        DefaultData.CATEGORY_SOCIAL_ACTIVITY_LABEL -> R.string.actionCategory_socialActivity_label
        DefaultData.CATEGORY_PRODUCTIVITY_LABEL -> R.string.actionCategory_productivity_label
        DefaultData.CATEGORY_RELAXATION_LABEL -> R.string.actionCategory_relaxation_label
        DefaultData.CATEGORY_HOBBIES_LABEL -> R.string.actionCategory_hobbies_label
        DefaultData.CATEGORY_ROUTINE_LABEL -> R.string.actionCategory_routine_label
        else -> null
    }
}