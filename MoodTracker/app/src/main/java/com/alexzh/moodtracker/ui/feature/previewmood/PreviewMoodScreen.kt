package com.alexzh.moodtracker.ui.feature.previewmood

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.chip.Chip
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.model.UiEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun PreviewMoodScreen(
    viewModel: PreviewMoodScreenViewModel,
    onNavigateToEditMood: (moodId: Long) -> Unit,
    onNavigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PreviewMoodScreenContent(
        uiState = uiState,
        onNavigateToEditMood = onNavigateToEditMood,
        onDeleteMood = { viewModel.onEvent(PreviewMoodScreenEvent.OnDeleteMood) },
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
fun PreviewMoodScreenContent(
    uiState: PreviewMoodScreenUiState,
    onNavigateToEditMood: (moodId: Long) -> Unit,
    onDeleteMood: () -> Unit,
    onNavigateUp: () -> Unit
) {
    var showDeleteMoodDialog by remember { mutableStateOf(false) }

    if (showDeleteMoodDialog) {
        DeleteMoodDialog(
            onConfirm = {
                onDeleteMood()
                showDeleteMoodDialog = false
            },
            onDismiss = { showDeleteMoodDialog = false }
        )
    }

    Scaffold(
        topBar = {
            PreviewMoodScreenToolbar(
                dateTime = uiState.dateTime,
                onNavigateToEditMood = { onNavigateToEditMood(uiState.moodId) },
                onDeleteMood = { showDeleteMoodDialog = true },
                onNavigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                uiState.mood != null -> MoodDetails(
                    mood = uiState.mood,
                    actions = uiState.actions,
                    note = uiState.note
                )
            }
        }
    }
}

@Composable
private fun MoodDetails(
    modifier: Modifier = Modifier,
    mood: LocalizedMood,
    actions: List<ActionItem>,
    note: String
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MoodInfo(mood = mood)
        Actions(actions = actions)
        Note(note = note)
    }
}

@Composable
private fun MoodInfo(
    modifier: Modifier = Modifier,
    mood: LocalizedMood
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(mood.icon),
            contentDescription = stringResource(mood.label),
            modifier = Modifier.size(42.dp),
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(mood.label),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun Actions(
    modifier: Modifier = Modifier,
    actions: List<ActionItem>
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        actions.forEach { action ->
            Chip(text = action.name)
        }
    }
}

@Composable
private fun Note(
    note: String
) {
    Text(text = note)
}

@Composable
fun DeleteMoodDialog(
    title: String = stringResource(R.string.common_deleteDialogTitle_label),
    text: String = stringResource(R.string.common_deleteDialogText_label),
    confirmButtonLabel: String = stringResource(R.string.common_delete_label),
    dismissButtonLabel: String = stringResource(R.string.common_cancel_label),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmButtonLabel)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dismissButtonLabel)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreviewMoodScreenToolbar(
    modifier: Modifier = Modifier,
    dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM),
    timeFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT),
    dateTime: LocalDateTime?,
    onNavigateToEditMood: () -> Unit,
    onDeleteMood: () -> Unit,
    onNavigateUp: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            val formattedDateTime = stringResource(
                R.string.previewMoodScreen_dateTime_label,
                dateTime?.format(dateFormatter) ?: "",
                dateTime?.format(timeFormatter) ?: ""
            )

            Text(
                text = if (dateTime != null) formattedDateTime else "",
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.common_navigateUp_contentDescription)
                )
            }
        },
        actions = {
            if (dateTime != null) {
                IconButton(onClick = onNavigateToEditMood) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = stringResource(R.string.previewMoodScreen_editMood_contentDescription)
                    )
                }
                IconButton(onClick = onDeleteMood) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = stringResource(R.string.previewMoodScreen_deleteMood_contentDescription)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun Preview_PreviewMoodScreen_Loading() {
    val uiState = PreviewMoodScreenUiState(
        isLoading = true
    )

    PreviewMoodScreenContent(
        uiState = uiState,
        onNavigateToEditMood = {},
        onDeleteMood = {},
        onNavigateUp = {}
    )
}

@Preview
@Composable
private fun Preview_PreviewMoodScreen_Success() {
    val uiState = PreviewMoodScreenUiState(
        dateTime = LocalDateTime.now(),
        mood = LocalizedMood.HAPPY,
        actions = listOf(
            ActionItem(1, "Running"),
            ActionItem(2, "Meditation"),
            ActionItem(3, "Reading")
        ),
        note = "Hello World"
    )

    PreviewMoodScreenContent(
        uiState = uiState,
        onNavigateToEditMood = {},
        onDeleteMood = {},
        onNavigateUp = {}
    )
}