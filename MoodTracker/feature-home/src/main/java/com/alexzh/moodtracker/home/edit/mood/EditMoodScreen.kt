package com.alexzh.moodtracker.home.edit.mood

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.alexzh.designsystem.component.bars.TopAppBarWithBackButton
import com.alexzh.designsystem.component.button.PrimaryButton
import com.alexzh.designsystem.component.dialog.DatePickerDialog
import com.alexzh.designsystem.component.dialog.TimePickerDialog
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.common.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.common.ui.model.UiEvent
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.R
import com.alexzh.moodtracker.home.edit.mood.components.ActionCategoriesSection
import com.alexzh.moodtracker.home.edit.mood.components.DateTimeSection
import com.alexzh.moodtracker.home.edit.mood.components.MoodSection
import com.alexzh.moodtracker.home.edit.mood.components.NoteSection
import com.alexzh.moodtracker.home.edit.mood.components.PhotosSection
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun EditMoodScreen(
    viewModel: EditMoodScreenViewModel,
    onNavigateToActionCategories: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    EditMoodScreenContent(
        uiState = uiState,
        onMoodChange = { mood -> viewModel.onEvent(EditMoodScreenEvent.OnMoodChange(mood)) },
        onNoteChange = { note -> viewModel.onEvent(EditMoodScreenEvent.OnNoteChange(note)) },
        onActionChange = { actionItem -> viewModel.onEvent(EditMoodScreenEvent.OnActionChange(actionItem)) },
        onDateChange = { date -> viewModel.onEvent(EditMoodScreenEvent.OnDateChange(date)) },
        onTimeChange = { time -> viewModel.onEvent(EditMoodScreenEvent.OnTimeChange(time)) },
        onPhotoChange = { action -> viewModel.onEvent(EditMoodScreenEvent.OnPhotoChange(action)) },
        onSave = { viewModel.onEvent(EditMoodScreenEvent.OnSave) },
        onNavigateToActionCategories = onNavigateToActionCategories,
        onNavigateUp = onNavigateUp
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                UiEvent.NavigateBack -> onNavigateUp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun EditMoodScreenContent(
    uiState: EditMoodScreenUiState,
    onMoodChange: (LocalizedMood) -> Unit,
    onNoteChange: (String) -> Unit,
    onActionChange: (ActionItem) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    onPhotoChange: (PhotoAction) -> Unit,
    onSave: () -> Unit,
    onNavigateToActionCategories: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val isDatePickerOpen = remember { mutableStateOf(false) }
    val isTimePickerOpen = remember { mutableStateOf(false) }

    if (isDatePickerOpen.value) {
        DatePickerDialog(
            selectedDate = uiState.selectedDate,
            onDateSelected = { date ->
                onDateChange(date)
                isDatePickerOpen.value = false
            },
            onDismiss = { isDatePickerOpen.value = false }
        )
    }

    if (isTimePickerOpen.value) {
        TimePickerDialog(
            selectedTime = uiState.selectedTime,
            onTimeSelected = { time ->
                onTimeChange(time)
                isTimePickerOpen.value = false
            },
            onDismiss = { isTimePickerOpen.value = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBarWithBackButton(
                title = stringResource(
                    if (uiState.isNewMood) {
                        R.string.editMoodScreen_addMood_label
                    } else R.string.editMoodScreen_editMood_label
                ),
                onBack = onNavigateUp,
                backButtonEnabled = !uiState.isLoading
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> {
                    EditMoodScreenExpandedContent(
                        uiState = uiState,
                        onMoodChange = onMoodChange,
                        onActionChange = onActionChange,
                        onDatePickerOpen = { isDatePickerOpen.value = true },
                        onTimePickerOpen = { isTimePickerOpen.value = true },
                        onNoteChange = onNoteChange,
                        onPhotoChange = onPhotoChange,
                        onSave = onSave,
                        onNavigateToActionCategories = onNavigateToActionCategories,
                        bringIntoViewRequester = bringIntoViewRequester,
                        focusManager = focusManager,
                    )
                }
                else -> {
                    EditMoodScreenCompactContent(
                        uiState = uiState,
                        onMoodChange = onMoodChange,
                        onActionChange = onActionChange,
                        onDatePickerOpen = { isDatePickerOpen.value = true },
                        onTimePickerOpen = { isTimePickerOpen.value = true },
                        onNoteChange = onNoteChange,
                        onPhotoChange = onPhotoChange,
                        onSave = onSave,
                        onNavigateToActionCategories = onNavigateToActionCategories,
                        bringIntoViewRequester = bringIntoViewRequester,
                        focusManager = focusManager,
                    )
                }
            }

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                        .clickable(enabled = false) { },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = stringResource(R.string.editMoodScreen_saving_label),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EditMoodScreenCompactContent(
    modifier: Modifier = Modifier,
    uiState: EditMoodScreenUiState,
    onMoodChange: (LocalizedMood) -> Unit,
    onActionChange: (ActionItem) -> Unit,
    onDatePickerOpen: () -> Unit,
    onTimePickerOpen: () -> Unit,
    onNoteChange: (String) -> Unit,
    onPhotoChange: (PhotoAction) -> Unit,
    onSave: () -> Unit,
    onNavigateToActionCategories: () -> Unit,
    bringIntoViewRequester: BringIntoViewRequester,
    focusManager: FocusManager,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MoodSection(
            items = LocalizedMood.entries.toList(),
            selectedMood = uiState.moodItems.selectedMood,
            iconShape = uiState.iconShape,
            onMoodSelected = onMoodChange
        )
        ActionCategoriesSection(
            items = uiState.actionCategoryItems,
            onNavigateToActionCategories = onNavigateToActionCategories,
            onActionChange = onActionChange
        )
        NoteSection(
            note = uiState.note,
            onNoteChange = onNoteChange,
            bringIntoViewRequester = bringIntoViewRequester,
            focusManager = focusManager
        )
        DateTimeSection(
            modifier = Modifier.padding(vertical = 8.dp),
            date = uiState.selectedDate,
            time = uiState.selectedTime,
            onDateChange = onDatePickerOpen,
            onTimeChange = onTimePickerOpen
        )
        PhotosSection(
            photos = uiState.photos,
            onPhotoChange = onPhotoChange
        )
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSave,
            enabled = uiState.canSave,
            text = stringResource(R.string.editMoodScreen_saveButton_label)
        )
    }
}

@Composable
private fun EditMoodScreenExpandedContent(
    modifier: Modifier = Modifier,
    uiState: EditMoodScreenUiState,
    onMoodChange: (LocalizedMood) -> Unit,
    onActionChange: (ActionItem) -> Unit,
    onDatePickerOpen: () -> Unit,
    onTimePickerOpen: () -> Unit,
    onNoteChange: (String) -> Unit,
    onPhotoChange: (PhotoAction) -> Unit,
    onSave: () -> Unit,
    onNavigateToActionCategories: () -> Unit,
    bringIntoViewRequester: BringIntoViewRequester,
    focusManager: FocusManager,
) {
    val leftPaneScrollState = rememberScrollState()
    val rightPaneScrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = modifier
                .weight(1.0f)
                .fillMaxHeight()
                .verticalScroll(leftPaneScrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MoodSection(
                items = LocalizedMood.entries.toList(),
                selectedMood = uiState.moodItems.selectedMood,
                iconShape = uiState.iconShape,
                onMoodSelected = onMoodChange
            )

            NoteSection(
                note = uiState.note,
                onNoteChange = onNoteChange,
                bringIntoViewRequester = bringIntoViewRequester,
                focusManager = focusManager
            )

            DateTimeSection(
                date = uiState.selectedDate,
                time = uiState.selectedTime,
                onDateChange = onDatePickerOpen,
                onTimeChange = onTimePickerOpen
            )

            PhotosSection(
                photos = uiState.photos,
                onPhotoChange = onPhotoChange
            )
        }

        Column(
            modifier = modifier
                .weight(1.0f)
                .fillMaxHeight()
        ) {
            ActionCategoriesSection(
                modifier = Modifier
                    .weight(1.0f)
                    .verticalScroll(rightPaneScrollState),
                items = uiState.actionCategoryItems,
                onNavigateToActionCategories = onNavigateToActionCategories,
                onActionChange = onActionChange
            )
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = onSave,
                enabled = uiState.canSave,
                text = stringResource(R.string.editMoodScreen_saveButton_label)
            )
        }
    }
}

@Preview(name = "Phone - Light", device = PHONE, showBackground = true)
@Preview(name = "Phone - Dark", device = PHONE, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Tablet - Light", device = PIXEL_TABLET, showBackground = true)
@Preview(name = "Tablet - Dark", device = PIXEL_TABLET, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview_EditMoodScreenContent(
    @PreviewParameter(EditMoodScreenUiStateProvider::class) uiState: EditMoodScreenUiState
) {
    AppTheme {
        EditMoodScreenContent(
            uiState = uiState,
            onMoodChange = { _ -> },
            onNoteChange = { _ -> },
            onActionChange = { _ -> },
            onDateChange = { _ -> },
            onTimeChange = { _ -> },
            onPhotoChange = { _ -> },
            onSave = { },
            onNavigateToActionCategories = { },
            onNavigateUp = { }
        )
    }
}

private val sampleCategories = mapOf(
    ActionCategoryItem(id = 1L, name = "Physical Activity") to listOf(
        ActionItem(id = 1L, name = "Running"),
        ActionItem(id = 2L, name = "Cycling"),
        ActionItem(id = 3L, name = "Swimming")
    ),
    ActionCategoryItem(id = 2L, name = "Work") to listOf(
        ActionItem(id = 4L, name = "Meeting"),
        ActionItem(id = 5L, name = "Commuting")
    )
)

class EditMoodScreenUiStateProvider : PreviewParameterProvider<EditMoodScreenUiState> {
    override val values: Sequence<EditMoodScreenUiState>
        get() = sequenceOf(
            EditMoodScreenUiState(
                isNewMood = true,
                moodItems = SelectableMoodItems(
                    selectedMood = null
                ),
                actionCategoryItems = SelectableActionCategories(
                    userActivityCategory = sampleCategories
                ),
                selectedDate = LocalDate.of(2025, 1, 1),
                selectedTime = LocalTime.of(12, 15),
                iconShape = IconShape.CIRCLE
            ),
            EditMoodScreenUiState(
                isNewMood = false,
                moodItems = SelectableMoodItems(
                    selectedMood = LocalizedMood.HAPPY
                ),
                actionCategoryItems = SelectableActionCategories(
                    userActivityCategory = sampleCategories,
                    selectedUserActivityIds = listOf(1L, 4L)
                ),
                selectedDate = LocalDate.of(2025, 1, 1),
                selectedTime = LocalTime.of(12, 15),
                note = "I had a great day",
                iconShape = IconShape.ROUNDED_SQUARE,
                photos = listOf(
                    "content://media/external/images/media/1".toUri(),
                )
            )
        )
}