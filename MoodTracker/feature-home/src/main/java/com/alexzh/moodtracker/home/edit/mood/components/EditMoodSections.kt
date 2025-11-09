package com.alexzh.moodtracker.home.edit.mood.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.alexzh.designsystem.component.button.IconButton
import com.alexzh.designsystem.component.chip.Chip
import com.alexzh.designsystem.component.media.PhotoThumbnailGrid
import com.alexzh.designsystem.component.section.CardSection
import com.alexzh.designsystem.component.section.Section
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.icon.DateRangeIcon
import com.alexzh.designsystem.icon.EditIcon
import com.alexzh.designsystem.icon.ScheduleIcon
import com.alexzh.moodtracker.home.R
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.edit.mood.PhotoAction
import com.alexzh.moodtracker.home.edit.mood.SelectableActionCategories
import com.alexzh.moodtracker.common.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@Composable
fun MoodSection(
    modifier: Modifier = Modifier,
    items: List<LocalizedMood>,
    selectedMood: LocalizedMood?,
    iconShape: IconShape,
    onMoodSelected: (mood: LocalizedMood) -> Unit
) {
    Section(
        modifier = modifier,
        title = stringResource(R.string.editMoodScreen_moodSection_label)
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            maxItemsInEachRow = 5
        ) {
            items.forEach { mood ->
                SelectableMoodItem(
                    modifier = Modifier.weight(1f),
                    mood = mood,
                    iconShape = iconShape,
                    isSelected = selectedMood == mood,
                    onSelected = { onMoodSelected(mood) }
                )
            }
        }
    }
}

@Composable
private fun SelectableMoodItem(
    modifier: Modifier = Modifier,
    mood: LocalizedMood,
    iconShape: IconShape,
    isSelected: Boolean,
    onSelected: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val backgroundColor = when (isSelected) {
        true -> MaterialTheme.colorScheme.secondaryContainer
        false -> MaterialTheme.colorScheme.surface
    }

    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .padding(vertical = 6.dp)
            .clickable(
                onClick = { onSelected() },
                interactionSource = interactionSource,
                indication = ripple(bounded = true),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier.size(42.dp),
            painter = painterResource(mood.getIcon(iconShape)),
            contentDescription = stringResource(mood.label)
        )
        Text(
            text = stringResource(mood.label),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ActionCategoriesSection(
    modifier: Modifier = Modifier,
    items: SelectableActionCategories,
    onNavigateToActionCategories: () -> Unit,
    onActionChange: (ActionItem) -> Unit
) {
    Section(
        modifier = modifier,
        title = stringResource(R.string.editMoodScreen_actionCategoriesSection_label),
        actions = {
            IconButton(
                onClick = onNavigateToActionCategories,
                icon = EditIcon,
                contentDescription = stringResource(R.string.editMoodScreen_manageActions_label)
            )
        }
    ) {
        items.userActivityCategory.forEach { (category, actions) ->
            ActionCategoryCard(
                title = category.name,
                actions = actions,
                selectedActionIds = items.selectedUserActivityIds,
                onActionChange = onActionChange
            )
        }
    }
}

@Composable
private fun ActionCategoryCard(
    modifier: Modifier = Modifier,
    title: String,
    actions: List<ActionItem>,
    selectedActionIds: List<Long>,
    onActionChange: (ActionItem) -> Unit
) {
    CardSection(
        modifier = modifier,
        title = title
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            actions.forEach {
                Chip(
                    text = it.name,
                    selected = selectedActionIds.contains(it.id),
                    onClick = { onActionChange(it) },
                )
            }
        }
    }
}

@Composable
fun NoteSection(
    modifier: Modifier = Modifier,
    note: String,
    onNoteChange: (String) -> Unit,
    bringIntoViewRequester: BringIntoViewRequester,
    focusManager: FocusManager
) {
    val coroutineScope = rememberCoroutineScope()

    Section(
        modifier = modifier
            .fillMaxWidth()
            .imePadding(),
        title = stringResource(R.string.editMoodScreen_noteSection_label)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            value = note,
            onValueChange = onNoteChange,
            minLines = 5,
            maxLines = 5,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
    }
}

@Composable
fun DateTimeSection(
    modifier: Modifier = Modifier,
    dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault()),
    timeFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault()),
    dateLabel: String = stringResource(R.string.editMoodScreen_date_label),
    timeLabel: String = stringResource(R.string.editMoodScreen_time_label),
    dateIcon: ImageVector = DateRangeIcon,
    timeIcon: ImageVector = ScheduleIcon,
    date: LocalDate,
    time: LocalTime,
    onDateChange: () -> Unit,
    onTimeChange: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DateTimeItem(
            label = dateLabel,
            icon = dateIcon,
            value = date.format(dateFormatter),
            onClick = onDateChange
        )
        DateTimeItem(
            label = timeLabel,
            icon = timeIcon,
            value = time.format(timeFormatter),
            onClick = onTimeChange
        )
    }
}

@Composable
private fun DateTimeItem(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .alpha(alpha = 0.8f),
            imageVector = icon,
            contentDescription = label
        )
        Text(
            modifier = Modifier
                .weight(1.0f)
                .padding(horizontal = 8.dp),
            text = label,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun PhotosSection(
    modifier: Modifier = Modifier,
    photos: List<Uri>,
    onPhotoChange: (PhotoAction) -> Unit
) {
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onPhotoChange(PhotoAction.Add(it)) }
    }

    Section(
        modifier = modifier.fillMaxWidth(),
        title = stringResource(R.string.editMoodScreen_photos_label)
    ) {
        PhotoThumbnailGrid(
            photos = photos,
            thumbnailSize = 80.dp,
            editable = true,
            onRemove = { index -> onPhotoChange(PhotoAction.Remove(index)) },
            onAddPhoto = { photoPicker.launch("image/*") },
            maxPhotos = 3
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_MoodSection_Selected_CircleIconShape() {
    AppTheme {
        MoodSection(
            items = LocalizedMood.entries.toList(),
            selectedMood = LocalizedMood.GOOD,
            iconShape = IconShape.CIRCLE,
            onMoodSelected = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_MoodSection_Unselected_RoundedSquareIconShape() {
    AppTheme {
        MoodSection(
            items = LocalizedMood.entries.toList(),
            selectedMood = LocalizedMood.GOOD,
            iconShape = IconShape.ROUNDED_SQUARE,
            onMoodSelected = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_ActionCategoriesSection() {
    AppTheme {
        ActionCategoriesSection(
            items = SelectableActionCategories(
                userActivityCategory = mapOf(
                    ActionCategoryItem(id = 1L, name = "Exercise") to listOf(
                        ActionItem(id = 1L, name = "Running"),
                        ActionItem(id = 2L, name = "Cycling"),
                        ActionItem(id = 3L, name = "Swimming")
                    ),
                    ActionCategoryItem(id = 2L, name = "Work") to listOf(
                        ActionItem(id = 4L, name = "Meeting"),
                        ActionItem(id = 5L, name = "Coding")
                    )
                ),
                selectedUserActivityIds = listOf(1L, 4L)
            ),
            onNavigateToActionCategories = {},
            onActionChange = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_NoteSection() {
    AppTheme {
        NoteSection(
            note = "This is a sample note about my mood today.",
            onNoteChange = {},
            bringIntoViewRequester = remember { BringIntoViewRequester() },
            focusManager = object : FocusManager {
                override fun clearFocus(force: Boolean) {}
                override fun moveFocus(focusDirection: FocusDirection): Boolean = false
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_DateTimeSection() {
    AppTheme {
        DateTimeSection(
            date = LocalDate.of(2025, 1, 1),
            time = LocalTime.of(12, 30),
            onDateChange = {},
            onTimeChange = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_PhotosSection_Empty() {
    AppTheme {
        PhotosSection(
            photos = emptyList(),
            onPhotoChange = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_PhotosSection_NotEmpty() {
    AppTheme {
        PhotosSection(
            photos = listOf(
                "content://media/external/images/media/1".toUri(),
                "content://media/external/images/media/2".toUri()
            ),
            onPhotoChange = {}
        )
    }
}

