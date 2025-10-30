package com.alexzh.moodtracker.ui.feature.editmood

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
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.bars.TopAppBarWithBackButton
import com.alexzh.moodtracker.ui.designsystem.button.PrimaryButton
import com.alexzh.moodtracker.ui.designsystem.dialog.DatePickerDialog
import com.alexzh.moodtracker.ui.designsystem.dialog.TimePickerDialog
import com.alexzh.moodtracker.ui.feature.editmood.components.ActionCategoriesSection
import com.alexzh.moodtracker.ui.feature.editmood.components.DateTimeSection
import com.alexzh.moodtracker.ui.feature.editmood.components.MoodSection
import com.alexzh.moodtracker.ui.feature.editmood.components.NoteSection
import com.alexzh.moodtracker.ui.feature.editmood.components.PhotosSection
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.model.UiEvent
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
    val context = LocalContext.current
    val windowSizeClass = calculateWindowSizeClass(context as android.app.Activity)
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
                onNavigateUp = onNavigateUp,
                backButtonEnabled = !uiState.isLoading,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
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
                else -> {
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