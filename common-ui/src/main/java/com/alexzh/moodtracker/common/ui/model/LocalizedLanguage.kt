package com.alexzh.moodtracker.common.ui.model

import com.alexzh.moodtracker.common.ui.R

enum class LocalizedLanguage(
    val tag: String,
    val label: Int
) {
    SYSTEM_DEFAULT("", R.string.common_language_systemDefault_label),
    ENGLISH("en", R.string.common_language_english_label),
    ARABIC("ar", R.string.common_language_arabic_label),
    RUSSIAN("ru", R.string.common_language_russian_label);

    companion object {
        fun fromLocaleTag(tag: String): LocalizedLanguage {
            return entries.firstOrNull { it.tag == tag } ?: SYSTEM_DEFAULT
        }
    }
}