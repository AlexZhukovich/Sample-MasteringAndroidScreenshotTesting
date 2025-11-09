package com.alexzh.moodtracker.settings.model

import androidx.annotation.StringRes
import com.alexzh.moodtracker.settings.R
import kotlinx.serialization.Serializable

@Serializable
enum class LocalizedIconShape(
    @StringRes val label: Int
) {
    CIRCLE(R.string.settingsScreen_iconShape_circle_label),
    ROUNDED_SQUARE(R.string.settingsScreen_iconShape_roundedSquare_label);
}