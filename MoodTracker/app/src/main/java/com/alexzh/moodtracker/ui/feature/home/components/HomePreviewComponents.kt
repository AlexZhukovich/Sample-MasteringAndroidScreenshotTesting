package com.alexzh.moodtracker.ui.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.domain.model.IconShape
import com.alexzh.moodtracker.ui.designsystem.button.IconButton
import com.alexzh.moodtracker.ui.designsystem.button.PrimaryIconButton
import com.alexzh.moodtracker.ui.designsystem.icon.MoodIcon
import com.alexzh.moodtracker.ui.model.MoodItem

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

@Composable
fun MoodPreviewActions(
    moodItem: MoodItem,
    onNavigateToEditMood: (Long) -> Unit,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrimaryIconButton(
            onClick = { onNavigateToEditMood(moodItem.id) },
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = stringResource(R.string.homeScreenPreview_editMood_contentDescription)
        )

        PrimaryIconButton(
            onClick = onDelete,
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = stringResource(R.string.homeScreenPreview_deleteMood_contentDescription)
        )
    }
}