package com.alexzh.moodtracker.ui.feature.home.components

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.domain.model.IconShape
import com.alexzh.moodtracker.ui.designsystem.icon.MoodIcon
import com.alexzh.moodtracker.ui.designsystem.media.AsyncImage
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.model.MoodItem
import com.alexzh.moodtracker.ui.theme.AppTheme
import java.time.LocalDateTime

@Composable
fun MoodItemCard(
    moodItem: MoodItem,
    iconShape: IconShape,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val cardModifier = if (isSelected) {
        modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
    } else {
        modifier.fillMaxWidth()
    }

    Card(
        modifier = cardModifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = onClick
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
                MoodIcon(
                    modifier = Modifier.size(36.dp),
                    mood =  moodItem.mood,
                    iconShape = iconShape
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
                PhotoThumbnails(images = moodItem.photos)
            }
        }
    }
}

@Composable
private fun PhotoThumbnails(
    images: List<Uri>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        images.forEach { imageUri ->
            AsyncImage(
                uri = imageUri,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview
@Composable
fun MoodItemCardPreview_Action_Note() {
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

@Preview
@Composable
fun MoodItemCardPreview_MultipleActions() {
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

@Preview
@Composable
fun MoodItemCardPreview_Note() {
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