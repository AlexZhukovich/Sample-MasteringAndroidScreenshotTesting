package com.alexzh.moodtracker.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alexzh.moodtracker.R
import kotlinx.serialization.Serializable

@Serializable
enum class LocalizedMood(
    val happiness: Float,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    ANGRY(1.0f, R.string.mood_angry, R.drawable.ic_mood_angry_colorful),
    SAD(2.0f, R.string.mood_sad, R.drawable.ic_mood_sad_colorful),
    OK(3.0f, R.string.mood_ok, R.drawable.ic_mood_ok_colorful),
    GOOD(4.0f, R.string.mood_good, R.drawable.ic_mood_good_colorful),
    HAPPY(5.0f, R.string.mood_happy, R.drawable.ic_mood_happy_colorful);

    companion object {
        fun fromHappiness(happiness: Float, default: LocalizedMood = OK): LocalizedMood =
            entries.find { it.happiness == happiness } ?: default
    }
}