package com.alexzh.moodtracker.ui.feature.actioncategorydetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.dialog.DeleteConfirmationDialog
import com.alexzh.moodtracker.ui.designsystem.empty.EmptyState
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.UiEvent

@Composable
fun ActionCategoryDetailsScreen(
    viewModel: ActionCategoryDetailsScreenViewModel,
    onNavigateUp: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ActionCategoryDetailsScreenContent(
        uiState = uiState.value,
        onDeleteAction = { actionId -> 
            viewModel.onEvent(ActionCategoryDetailsScreenEvent.OnDeleteAction(actionId))
        },
        onEditAction = { actionId, actionName -> 
            viewModel.onEvent(ActionCategoryDetailsScreenEvent.OnEditAction(actionId, actionName))
        },
        onAddAction = { actionName ->
            viewModel.onEvent(ActionCategoryDetailsScreenEvent.OnAddAction(actionName))
        },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCategoryDetailsScreenContent(
    uiState: ActionCategoryDetailsScreenUiState,
    onDeleteAction: (actionId: Long) -> Unit,
    onEditAction: (actionId: Long, actionName: String) -> Unit,
    onAddAction: (actionName: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    var showEditActionDialog by remember { mutableStateOf(false) }
    var editingAction by remember { mutableStateOf<ActionItem?>(null) }
    var showAddActionDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var deletingAction by remember { mutableStateOf<ActionItem?>(null) }

    editingAction?.let { action ->
        if (showEditActionDialog) {
            EditActionDialog(
                action = action,
                onDismiss = {
                    showEditActionDialog = false
                    editingAction = null
                },
                onSave = { actionId, actionName ->
                    onEditAction(actionId, actionName)
                    showEditActionDialog = false
                    editingAction = null
                }
            )
        }
    }

    deletingAction?.let { action ->
        if (showDeleteConfirmationDialog) {
            DeleteConfirmationDialog(
                title = stringResource(R.string.actionCategoryDetailsScreen_deleteActionDialog_title),
                text = stringResource(R.string.actionCategoryDetailsScreen_deleteActionDialog_label, action.name),
                onDismiss = {
                    showDeleteConfirmationDialog = false
                    deletingAction = null
                },
                onConfirm = {
                    onDeleteAction(action.id)
                    showDeleteConfirmationDialog = false
                    deletingAction = null
                }
            )
        }
    }

    if (showAddActionDialog) {
        AddActionDialog(
            onDismiss = {
                showAddActionDialog = false
            },
            onSave = { actionName ->
                onAddAction(actionName)
                showAddActionDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            ActionCategoryDetailsScreenTopAppBar(
                title = uiState.category?.name ?: "",
                onNavigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddActionDialog = true }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.actionCategoryDetailsScreen_addActionButton_label)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                uiState.actions.isEmpty() -> EmptyState(
                    text = stringResource(R.string.actionCategoryDetailsScreen_emptyState_label)
                )
                else -> ActionList(
                    actions = uiState.actions,
                    onEditAction = { action ->
                        editingAction = action
                        showEditActionDialog = true
                    },
                    onDeleteAction = { action ->
                        deletingAction = action
                        showDeleteConfirmationDialog = true
                    }
                )
            }
        }
    }
}

@Composable
private fun ActionList(
    modifier: Modifier = Modifier,
    actions: List<ActionItem>,
    onEditAction: (ActionItem) -> Unit,
    onDeleteAction: (ActionItem) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(actions) { action ->
            ActionCard(
                action = action,
                onEditAction = { onEditAction(action) },
                onDeleteAction = { onDeleteAction(action) }
            )
        }
    }
}

@Composable
private fun ActionCard(
    modifier: Modifier = Modifier,
    action: ActionItem,
    onEditAction: () -> Unit,
    onDeleteAction: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier.weight(1.0f),
                text = action.name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = onEditAction) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = stringResource(R.string.actionCategoryDetailsScreen_editAction_contentDescription)
                )
            }
            IconButton(onClick = onDeleteAction) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.actionCategoryDetailsScreen_deleteAction_contentDescription)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCategoryDetailsScreenTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onNavigateUp) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.common_navigateUp_contentDescription)
                )
            }
        }
    )
}

@Composable
private fun EditActionDialog(
    action: ActionItem,
    onDismiss: () -> Unit,
    onSave: (actionId: Long, actionName: String) -> Unit
) {
    var actionName by remember { mutableStateOf(action.name) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.actionCategoryDetailsScreen_editActionDialog_title),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            OutlinedTextField(
                value = actionName,
                onValueChange = { actionName = it },
                label = { Text(stringResource(R.string.actionCategoryDetailsScreen_editActionDialog_actionName_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(action.id, actionName.trim()) },
                enabled = actionName.trim().isNotEmpty() && actionName.trim() != action.name
            ) {
                Text(stringResource(R.string.common_save_label))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.common_cancel_label))
            }
        }
    )
}

@Composable
private fun AddActionDialog(
    onDismiss: () -> Unit,
    onSave: (actionName: String) -> Unit
) {
    var actionName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.actionCategoryDetailsScreen_addActionDialog_title),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            OutlinedTextField(
                value = actionName,
                onValueChange = { actionName = it },
                label = { Text(stringResource(R.string.actionCategoryDetailsScreen_addActionDialog_actionName_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(actionName.trim()) },
                enabled = actionName.trim().isNotEmpty()
            ) {
                Text(stringResource(R.string.common_save_label))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.common_cancel_label))
            }
        }
    )
}