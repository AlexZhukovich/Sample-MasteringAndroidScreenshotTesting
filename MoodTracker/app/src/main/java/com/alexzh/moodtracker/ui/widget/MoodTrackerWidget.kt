package com.alexzh.moodtracker.ui.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.alexzh.moodtracker.MainActivity
import com.alexzh.moodtracker.ui.model.LocalizedMood

class MoodTrackerWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                MoodTrackerWidgetContent()
            }
        }
    }
}

@Composable
private fun MoodTrackerWidgetContent() {
    Row(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(GlanceTheme.colors.surface)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LocalizedMood.entries.forEach { mood ->
            MoodButton(
                mood = mood,
                modifier = GlanceModifier
                    .defaultWeight()
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
private fun MoodButton(
    mood: LocalizedMood,
    modifier: GlanceModifier = GlanceModifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.clickable(
            actionStartActivity(createEditMoodScreenIntent(context, mood.happiness))
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            provider = ImageProvider(mood.icon),
            contentDescription = context.getString(mood.label),
            modifier = GlanceModifier.size(48.dp)
        )
        Text(
            text = context.getString(mood.label),
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
                fontSize = 12.sp
            )
        )
    }
}

private fun createEditMoodScreenIntent(
    context: Context,
    happiness: Float
) = Intent(
    Intent.ACTION_VIEW,
    "moodtracker://editMood?preselectedHappiness=${happiness}".toUri(),
    context,
    MainActivity::class.java
)