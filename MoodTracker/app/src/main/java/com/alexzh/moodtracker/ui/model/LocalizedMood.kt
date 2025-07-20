package com.alexzh.moodtracker.ui.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alexzh.moodtracker.R

enum class LocalizedMood(val happiness: Float, @StringRes val titleRes: Int, val emoji: String) {
    ANGRY(1.0f, R.string.mood_angry, "😠"),
    SAD(2.0f, R.string.mood_sad, "😢"),
    OK(3.0f, R.string.mood_ok, "😐"),
    GOOD(4.0f, R.string.mood_good, "😊"),
    HAPPY(5.0f, R.string.mood_happy, "😄");

    val title: String
        @Composable
        get() = stringResource(titleRes)

    companion object {
        fun fromHappiness(happiness: Float, default: LocalizedMood = OK): LocalizedMood =
            entries.find { it.happiness == happiness } ?: default
    }
}