package com.alexzh.moodtracker.widget.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.datastore.preferences.core.Preferences
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
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.widget.R
import com.alexzh.moodtracker.widget.model.WidgetTheme

class MoodTrackerWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = WidgetStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val widgetSettings = currentState<Preferences>().toWidgetSettings()

            GlanceTheme(colors = widgetSettings.theme.toGlanceColors()) {
                MoodTrackerWidgetContent(
                    iconShape = widgetSettings.iconShape,
                    transparency = widgetSettings.transparency
                )
            }
        }
    }

    override suspend fun providePreview(context: Context, widgetCategory: Int) {
        val isDarkTheme = (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        val theme = if (isDarkTheme) WidgetTheme.DARK else WidgetTheme.LIGHT

        provideContent {
            GlanceTheme(colors = theme.toGlanceColors()) {
                MoodTrackerWidgetContent(
                    iconShape = IconShape.CIRCLE,
                    transparency = 0f
                )
            }
        }
    }
}

@Composable
private fun MoodTrackerWidgetContent(
    iconShape: IconShape,
    transparency: Float
) {
    val context = LocalContext.current
    val backgroundColor = GlanceTheme.colors.background
    val textColor = GlanceTheme.colors.onBackground

    Column(
        modifier = GlanceModifier.fillMaxSize()
            .background(backgroundColor.getColor(context).copy(alpha = 1f - transparency))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            text = context.getString(R.string.widget_title),
            style = TextStyle(
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
        )
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LocalizedMood.entries.forEach { mood ->
                MoodButton(
                    modifier = GlanceModifier.defaultWeight(),
                    mood = mood,
                    iconShape = iconShape
                )
            }
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

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            provider = ImageProvider(mood.getIcon(iconShape)),
            contentDescription = context.getString(mood.label),
            modifier = GlanceModifier
                .size(48.dp)
                .padding(4.dp)
                .clickable(actionStartActivity(createEditMoodScreenIntent(context, mood)))
        )
    }
}

private fun createEditMoodScreenIntent(
    context: Context,
    mood: LocalizedMood
): Intent {
    val uri = "moodtracker://editMood?preselectedMood=${mood.name}".toUri()
    return Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage(context.packageName)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}