package com.alexzh.moodtracker.ui.feature.editmood

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.button.PrimaryButton
import com.alexzh.moodtracker.ui.designsystem.core.modifier.circleLayout
import com.alexzh.moodtracker.ui.designsystem.dialog.DatePickerDialog
import com.alexzh.moodtracker.ui.designsystem.dialog.TimePickerDialog
import com.alexzh.moodtracker.ui.designsystem.section.CardSection
import com.alexzh.moodtracker.ui.designsystem.section.Section
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.model.UiEvent
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

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

@Composable
fun EditMoodScreenContent(
    uiState: EditMoodScreenUiState,
    onMoodChange: (LocalizedMood) -> Unit,
    onNoteChange: (String) -> Unit,
    onActionChange: (ActionItem) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    onSave: () -> Unit,
    onNavigateToActionCategories: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scrollState = rememberScrollState()
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
            EditMoodScreenTopAppBar(
                newMood = uiState.isNewMood,
                onNavigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MoodSection(
                items = LocalizedMood.entries.toList(),
                selectedMood = uiState.moodItems.selectedMood,
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
                onDateChange = { isDatePickerOpen.value = true },
                onTimeChange = { isTimePickerOpen.value = true }
            )
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSave,
                enabled = uiState.canSave,
                text = stringResource(R.string.editMoodScreen_saveButton_label)
            )
        }
    }
}

@Composable
fun MoodSection(
    modifier: Modifier = Modifier,
    items: List<LocalizedMood>,
    selectedMood: LocalizedMood?,
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
                    isSelected = selectedMood == mood,
                    icon = mood.icon,
                    label = mood.label,
                    onSelected = { onMoodSelected(mood) }
                )
            }
        }
    }
}

@Composable
fun SelectableMoodItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    @DrawableRes icon: Int,
    @StringRes label: Int,
    onSelected: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .clickable(
                onClick = { onSelected() },
                interactionSource = interactionSource,
                indication = ripple(bounded = true),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = stringResource(label),
            modifier = Modifier
                .size(52.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                )
                .then(
                    if (isSelected)
                        Modifier.border(
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.primary
                            ), CircleShape
                        )
                    else
                        Modifier
                )
                .circleLayout()
                .padding(4.dp),
        )
        Text(
            text = stringResource(label),
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
            IconButton(onClick = onNavigateToActionCategories) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = stringResource(R.string.editMoodScreen_manageActions_label)
                )
            }
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
fun ActionCategoryCard(
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
                FilterChip(
                    modifier = Modifier
                        .height(36.dp)
                        .padding(vertical = 2.dp),
                    selected = selectedActionIds.contains(it.id),
                    onClick = { onActionChange(it) },
                    label = {
                        Text(it.name)
                    }
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
private fun DateTimeSection(
    modifier: Modifier = Modifier,
    dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault()),
    timeFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault()),
    dateLabel: String = stringResource(R.string.common_date_label),
    timeLabel: String = stringResource(R.string.common_time_label),
    dateIcon: Painter = painterResource(R.drawable.ic_date_range),
    timeIcon: Painter = painterResource(R.drawable.ic_schedule),
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
fun DateTimeItem(
    modifier: Modifier = Modifier,
    label: String,
    icon: Painter,
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
            modifier = Modifier.size(24.dp)
                .alpha(alpha = 0.8f),
            painter = icon,
            contentDescription = label
        )
        Text(
            modifier = Modifier.weight(1.0f)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditMoodScreenTopAppBar(
    modifier: Modifier = Modifier,
    newMood: Boolean,
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                if (newMood) {
                    stringResource(R.string.editMoodScreen_addMood_label)
                } else {
                    stringResource(R.string.editMoodScreen_editMood_label)
                }
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.common_navigateUp_contentDescription)
                )
            }
        }
    )
}

