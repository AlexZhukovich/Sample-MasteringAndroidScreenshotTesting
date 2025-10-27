package com.alexzh.moodtracker.ui.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.domain.model.IconShape
import com.alexzh.moodtracker.ui.designsystem.button.IconButton
import com.alexzh.moodtracker.ui.designsystem.icon.MoodIcon
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.model.MoodItem
import com.alexzh.moodtracker.ui.theme.AppTheme
import java.time.LocalDateTime

@Composable
fun MoodPreviewHeader(
    moodItem: MoodItem,
    iconShape: IconShape,
    windowWidthSizeClass: WindowWidthSizeClass,
    onNavigateToEditMood: (Long) -> Unit,
    onClose: () -> Unit,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MoodIcon(
                modifier = Modifier.size(48.dp),
                mood = moodItem.mood,
                iconShape = iconShape
            )
            Column {
                Text(
                    text = stringResource(moodItem.mood.label),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = moodItem.formattedDate,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (windowWidthSizeClass == WindowWidthSizeClass.Compact || windowWidthSizeClass == WindowWidthSizeClass.Medium) {
            Row {
                IconButton(
                    onClick = { onNavigateToEditMood(moodItem.id) },
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = stringResource(R.string.homeScreenPreview_editMood_contentDescription)
                )
                IconButton(
                    onClick = onDelete,
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.homeScreenPreview_deleteMood_contentDescription)
                )
            }
        } else {
            IconButton(
                onClick = onClose,
                painter = painterResource(R.drawable.ic_close),
                contentDescription = stringResource(R.string.homeScreenPreview_close_contentDescription)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview_MoodPreviewHeader_Compact_CircleIconShape() {
    val moodItem =MoodItem(
        id = 1L,
        mood = LocalizedMood.GOOD,
        date = LocalDateTime.of(2025, 1, 15, 14, 30),
        note = "",
        actions = emptyList()
    )

    AppTheme {
        MoodPreviewHeader(
            moodItem = moodItem,
            iconShape = IconShape.CIRCLE,
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            onNavigateToEditMood = {},
            onClose = {},
            onDelete = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_MoodPreviewHeader_Medium_RoundedSquareIconShape() {
    val moodItem =MoodItem(
        id = 1L,
        mood = LocalizedMood.HAPPY,
        date = LocalDateTime.of(2025, 1, 15, 10, 0),
        note = "",
        actions = emptyList()
    )

    AppTheme {
        MoodPreviewHeader(
            moodItem = moodItem,
            iconShape = IconShape.ROUNDED_SQUARE,
            windowWidthSizeClass = WindowWidthSizeClass.Medium,
            onNavigateToEditMood = {},
            onClose = {},
            onDelete = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_MoodPreviewHeader_Expanded_CircleIconShape() {
    AppTheme {
        MoodPreviewHeader(
            moodItem = MoodItem(
                id = 1L,
                mood = LocalizedMood.OK,
                date = LocalDateTime.of(2025, 1, 15, 18, 45),
                note = "",
                actions = emptyList()
            ),
            iconShape = IconShape.CIRCLE,
            windowWidthSizeClass = WindowWidthSizeClass.Expanded,
            onNavigateToEditMood = {},
            onClose = {},
            onDelete = {}
        )
    }
}