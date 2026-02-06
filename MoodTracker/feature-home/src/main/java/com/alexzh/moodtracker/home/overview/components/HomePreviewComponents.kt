package com.alexzh.moodtracker.home.overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.component.button.IconButton
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.icon.CloseIcon
import com.alexzh.designsystem.icon.DeleteIcon
import com.alexzh.designsystem.icon.EditIcon
import com.alexzh.moodtracker.home.R
import com.alexzh.designsystem.R as DesignSystemR
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import java.time.LocalDateTime

@Composable
fun MoodPreviewHeader(
    moodItem: MoodItem,
    iconShape: IconShape,
    isLayoutExpanded: Boolean,
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
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(moodItem.mood.getIcon(iconShape)),
                contentDescription = stringResource(moodItem.mood.label)
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

        if (isLayoutExpanded) {
            IconButton(
                onClick = onClose,
                icon = CloseIcon,
                contentDescription = stringResource(DesignSystemR.string.closeButton_contentDescription)
            )
        } else {
            Row {
                IconButton(
                    onClick = { onNavigateToEditMood(moodItem.id) },
                    icon = EditIcon,
                    contentDescription = stringResource(R.string.homeScreenPreview_editMood_contentDescription)
                )
                IconButton(
                    onClick = onDelete,
                    icon = DeleteIcon,
                    contentDescription = stringResource(R.string.homeScreenPreview_deleteMood_contentDescription)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_MoodPreviewHeader_NonExpandedLayout_RoundedSquareIconShape() {
    val moodItem = MoodItem(
        id = 1L,
        mood = LocalizedMood.HAPPY,
        date = LocalDateTime.of(2025, 1, 15, 10, 0),
        note = "",
        actions = emptyList()
    )

    AppTheme {
        Surface {
            MoodPreviewHeader(
                moodItem = moodItem,
                iconShape = IconShape.ROUNDED_SQUARE,
                isLayoutExpanded = false,
                onNavigateToEditMood = {},
                onClose = {},
                onDelete = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_MoodPreviewHeader_Expanded_CircleIconShape() {
    AppTheme {
        Surface {
            MoodPreviewHeader(
                moodItem = MoodItem(
                    id = 1L,
                    mood = LocalizedMood.OK,
                    date = LocalDateTime.of(2025, 1, 15, 18, 45),
                    note = "",
                    actions = emptyList()
                ),
                iconShape = IconShape.CIRCLE,
                isLayoutExpanded = true,
                onNavigateToEditMood = {},
                onClose = {},
                onDelete = {}
            )
        }
    }
}