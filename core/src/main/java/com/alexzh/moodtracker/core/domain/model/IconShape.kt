package com.alexzh.moodtracker.core.domain.model

enum class IconShape {
    CIRCLE,
    ROUNDED_SQUARE;

    companion object {
        fun fromString(value: String): IconShape {
            return entries.find { it.name == value }
                ?: CIRCLE
        }
    }
}