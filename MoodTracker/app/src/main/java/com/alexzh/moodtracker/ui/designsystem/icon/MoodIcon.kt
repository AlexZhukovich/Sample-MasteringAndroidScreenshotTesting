package com.alexzh.moodtracker.ui.designsystem.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.domain.model.IconShape
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.theme.AppTheme

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

@Preview
@Composable
fun Preview_MoodIcon() {
    AppTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LocalizedMood.entries.forEach { mood ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MoodIcon(
                        mood = mood,
                        iconShape = IconShape.CIRCLE
                    )
                    MoodIcon(
                        mood = mood,
                        iconShape = IconShape.ROUNDED_SQUARE
                    )
                }
            }
        }
    }
}