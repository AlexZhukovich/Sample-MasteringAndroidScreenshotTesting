package com.alexzh.moodtracker.ui.designsystem.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexzh.moodtracker.domain.model.IconShape
import com.alexzh.moodtracker.ui.model.LocalizedMood

@Composable
fun MoodIcon(
    modifier: Modifier = Modifier,
    mood: LocalizedMood,
    iconShape: IconShape
) {
    Image(
        modifier = modifier,
        painter = painterResource(mood.getIcon(iconShape)),
        contentDescription = stringResource(mood.label),
    )
}