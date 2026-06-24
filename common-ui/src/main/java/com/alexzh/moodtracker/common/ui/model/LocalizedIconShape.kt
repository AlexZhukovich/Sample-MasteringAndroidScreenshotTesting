package com.alexzh.moodtracker.common.ui.model

import com.alexzh.moodtracker.common.ui.R
import com.alexzh.moodtracker.core.domain.model.IconShape

enum class LocalizedIconShape(
    val label: Int
) {
    CIRCLE(R.string.common_iconShape_circle_label),
    ROUNDED_SQUARE(R.string.common_iconShape_roundedSquare_label);

    fun toIconShape(): IconShape {
        return when (this) {
            CIRCLE -> IconShape.CIRCLE
            ROUNDED_SQUARE -> IconShape.ROUNDED_SQUARE
        }
    }

    companion object {
        fun fromIconShape(iconShape: IconShape): LocalizedIconShape {
            return when (iconShape) {
                IconShape.CIRCLE -> CIRCLE
                IconShape.ROUNDED_SQUARE -> ROUNDED_SQUARE
            }
        }
    }
}