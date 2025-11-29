package com.alexzh.moodtracker.home.overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.alexzh.designsystem.component.media.PhotoThumbnailGrid
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import java.time.LocalDateTime

@Composable
fun MoodItemCard(
    moodItem: MoodItem,
    iconShape: IconShape,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val colors = if (isSelected) {
        CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    } else {
        CardDefaults.elevatedCardColors()
    }

    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = onClick,
        colors = colors
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(moodItem.mood.getIcon(iconShape)),
                    contentDescription = stringResource(moodItem.mood.label)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1.0f),
                            text = stringResource(moodItem.mood.label),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = moodItem.formattedDate,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = moodItem.actions.joinToString(", ") { it.name },
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (moodItem.note.isNotBlank()) {
                Text(
                    text = moodItem.note,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (moodItem.photos.isNotEmpty()) {
                PhotoThumbnailGrid(
                    photos = moodItem.photos,
                    thumbnailSize = 40.dp,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun MoodItemCardPreview_Action_Note_Photos() {
    AppTheme {
        MoodItemCard(
            moodItem = MoodItem(
                id = 1L,
                mood = LocalizedMood.GOOD,
                date = LocalDateTime.of(2023, 1, 1, 19, 0),
                note = "I had a productive day",
                actions = listOf(
                    ActionItem(id = 1L, name = "Work")
                ),
                photos = listOf(
                    "content://media/external/images/media/1".toUri(),
                    "content://media/external/images/media/2".toUri()
                )
            ),
            iconShape = IconShape.CIRCLE,
            onClick = {}
        )
    }
}

@PreviewLightDark
@Composable
fun MoodItemCardPreview_Action_Note_NoPhotos() {
    AppTheme {
        MoodItemCard(
            moodItem = MoodItem(
                id = 1L,
                mood = LocalizedMood.GOOD,
                date = LocalDateTime.of(2023, 1, 1, 19, 0),
                note = "I had a productive day",
                actions = listOf(
                    ActionItem(id = 1L, name = "Work")
                )
            ),
            iconShape = IconShape.CIRCLE,
            onClick = {}
        )
    }
}

@PreviewLightDark
@Composable
fun MoodItemCardPreview_MultipleActions_Notes_NoPhotos() {
    AppTheme {
        MoodItemCard(
            moodItem = MoodItem(
                id = 1L,
                mood = LocalizedMood.OK,
                date = LocalDateTime.of(2023, 1, 1, 10, 0),
                note = "",
                actions = listOf(
                    ActionItem(id = 1L, name = "Work"),
                    ActionItem(id = 2L, name = "Commute"),
                )
            ),
            iconShape = IconShape.ROUNDED_SQUARE,
            onClick = {}
        )
    }
}

@PreviewLightDark
@Composable
fun MoodItemCardPreview_NoAction_Note_NoPhotos() {
    AppTheme {
        MoodItemCard(
            moodItem = MoodItem(
                id = 1L,
                mood = LocalizedMood.OK,
                date = LocalDateTime.of(2023, 1, 1, 7, 30),
                note = "I had difficulty getting up from bed",
                actions = emptyList()
            ),
            iconShape = IconShape.CIRCLE,
            onClick = {}
        )
    }
}