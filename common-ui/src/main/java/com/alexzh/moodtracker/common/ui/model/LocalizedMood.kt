package com.alexzh.moodtracker.common.ui.model

import androidx.annotation.DrawableRes
import com.alexzh.moodtracker.common.ui.R
import com.alexzh.moodtracker.core.domain.model.IconShape

enum class LocalizedMood(
    val happiness: Float,
    val label: Int,
    val circleIcon: Int,
    val roundedSquareIcon: Int
) {
    ANGRY(1.0f, R.string.mood_angry, R.drawable.ic_mood_angry_circle, R.drawable.ic_mood_angry_rounded_square),
    SAD(2.0f, R.string.mood_sad, R.drawable.ic_mood_sad_circle, R.drawable.ic_mood_sad_rounded_square),
    OK(3.0f, R.string.mood_ok, R.drawable.ic_mood_ok_circle, R.drawable.ic_mood_ok_rounded_square),
    GOOD(4.0f, R.string.mood_good, R.drawable.ic_mood_good_circle, R.drawable.ic_mood_good_rounded_square),
    HAPPY(5.0f, R.string.mood_happy, R.drawable.ic_mood_happy_circle, R.drawable.ic_mood_happy_rounded_square);

    @DrawableRes
    fun getIcon(iconShape: IconShape): Int {
        return when(iconShape) {
            IconShape.CIRCLE -> circleIcon
            IconShape.ROUNDED_SQUARE -> roundedSquareIcon
        }
    }

    companion object {
        fun fromHappiness(happiness: Float, default: LocalizedMood = OK): LocalizedMood =
            entries.find { it.happiness == happiness } ?: default
    }
}