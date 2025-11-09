package com.alexzh.moodtracker.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.alexzh.moodtracker.core.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import org.koin.compose.koinInject

class MoodTrackerWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val settingsDataSource = koinInject<SettingsDataSource>()
            val iconShape by settingsDataSource.getIconShape().collectAsState(initial = IconShape.CIRCLE)

            GlanceTheme {
                MoodTrackerWidgetContent(
                    iconShape = iconShape
                )
            }
        }
    }
}

@Composable
private fun MoodTrackerWidgetContent(
    iconShape: IconShape
) {
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
                modifier = GlanceModifier
                    .defaultWeight()
                    .padding(horizontal = 4.dp),
                mood = mood,
                iconShape = iconShape
            )
        }
    }
}

@Composable
private fun MoodButton(
    modifier: GlanceModifier = GlanceModifier,
    mood: LocalizedMood,
    iconShape: IconShape
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.clickable(
            actionStartActivity(createEditMoodScreenIntent(context, mood))
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            provider = ImageProvider(mood.getIcon(iconShape)),
            contentDescription = context.getString(mood.label),
            modifier = GlanceModifier.size(48.dp).padding(4.dp)
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
    mood: LocalizedMood
) = Intent(
    Intent.ACTION_VIEW,
    "moodtracker://editMood?preselectedMood=${mood.name}".toUri(),
    context,
    MainActivity::class.java
)