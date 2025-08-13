package com.alexzh.moodtracker.ui.feature.actioncategories

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.alexzh.moodtracker.domain.PastelAccentColor
import com.alexzh.moodtracker.ui.designsystem.color.ColorSelectionGrid
import com.alexzh.moodtracker.ui.designsystem.dialog.DeleteConfirmationDialog
import com.alexzh.moodtracker.ui.designsystem.empty.EmptyState
import com.alexzh.moodtracker.ui.model.ActionCategoryItem

@Composable
fun ActionCategoriesScreen(
    viewModel: ActionCategoriesScreenViewModel,
    onNavigateToEditActionCategory: (actionCategoryId: Long) -> Unit,
    onNavigateUp: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ActionCategoriesScreenContent(
        uiState = uiState.value,
        onNavigateToEditActionCategory = onNavigateToEditActionCategory,
        onDeleteActionCategory = { actionCategoryId ->
            viewModel.onEvent(ActionCategoriesScreenEvent.OnDeleteActionCategory(actionCategoryId))
        },
        onEditActionCategory = { categoryId, name, color ->
            viewModel.onEvent(ActionCategoriesScreenEvent.OnEditActionCategory(categoryId, name, color))
        },
        onAddActionCategory = { name, color ->
            viewModel.onEvent(ActionCategoriesScreenEvent.OnAddActionCategory(name, color))
        },
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun ActionCategoriesScreenContent(
    uiState: ActionCategoriesScreenUiState,
    onNavigateToEditActionCategory: (actionCategoryId: Long) -> Unit,
    onDeleteActionCategory: (actionCategoryId: Long) -> Unit,
    onEditActionCategory: (categoryId: Long, name: String, color: PastelAccentColor) -> Unit,
    onAddActionCategory: (name: String, color: PastelAccentColor) -> Unit,
    onNavigateUp: () -> Unit
) {
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var deletingCategory by remember { mutableStateOf<ActionCategoryItem?>(null) }
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    var showEditCategoryDialog by remember { mutableStateOf(false) }
    var editingCategory by remember { mutableStateOf<ActionCategoryItem?>(null) }

    editingCategory?.let { category ->
        if (showEditCategoryDialog) {
            EditCategoryDialog(
                category = category,
                onDismiss = {
                    showEditCategoryDialog = false
                    editingCategory = null
                },
                onSave = { name, color ->
                    onEditActionCategory(category.id, name, color)
                    showEditCategoryDialog = false
                    editingCategory = null
                }
            )
        }
    }

    deletingCategory?.let { category ->
        if (showDeleteConfirmationDialog) {
            DeleteConfirmationDialog(
                title = stringResource(R.string.actionCategoriesScreen_deleteCategoryDialog_title),
                text = stringResource(R.string.actionCategoriesScreen_deleteCategoryDialog_label, category.name),
                onDismiss = {
                    showDeleteConfirmationDialog = false
                    deletingCategory = null
                },
                onConfirm = {
                    onDeleteActionCategory(category.id)
                    showDeleteConfirmationDialog = false
                    deletingCategory = null
                }
            )
        }
    }

    if (showAddCategoryDialog) {
        AddCategoryDialog(
            onDismiss = {
                showAddCategoryDialog = false
            },
            onSave = { name, color ->
                onAddActionCategory(name, color)
                showAddCategoryDialog = false
            }
        )
    }
    
    Scaffold(
        topBar = {
            ActionCategoriesScreenTopAppBar(
                onNavigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddCategoryDialog = true }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.actionCategoriesScreen_addCategoryButton_label)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                uiState.categories.isEmpty() -> EmptyState(
                    text = stringResource(R.string.actionCategoriesScreen_emptyState_label)
                )
                else -> ActionCategoriesList(
                    categories = uiState.categories,
                    onActionCategoryClick = onNavigateToEditActionCategory,
                    onEditCategory = { category ->
                        editingCategory = category
                        showEditCategoryDialog = true
                    },
                    onDeleteCategory = { category ->
                        deletingCategory = category
                        showDeleteConfirmationDialog = true
                    }
                )
            }
        }
    }
}

@Composable
private fun ActionCategoriesList(
    modifier: Modifier = Modifier,
    categories: List<ActionCategoryItem>,
    onActionCategoryClick: (Long) -> Unit,
    onEditCategory: (ActionCategoryItem) -> Unit,
    onDeleteCategory: (ActionCategoryItem) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            ActionCategoryCard(
                category = category,
                onActionCategoryClick = onActionCategoryClick,
                onEditCategory = onEditCategory,
                onDeleteCategory = onDeleteCategory
            )
        }
    }
}

@Composable
private fun ActionCategoryCard(
    modifier: Modifier = Modifier,
    category: ActionCategoryItem,
    onActionCategoryClick: (Long) -> Unit,
    onEditCategory: (ActionCategoryItem) -> Unit,
    onDeleteCategory: (ActionCategoryItem) -> Unit,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { onActionCategoryClick(category.id) }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                modifier = Modifier.size(36.dp),
                shape = CircleShape,
                color = category.color.getColor(isDarkTheme)
            ) {}
            Text(
                modifier = Modifier.weight(1.0f)
                    .padding(start = 4.dp),
                text = category.name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = { onEditCategory(category) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = stringResource(R.string.actionCategoriesScreen_editCategory_contentDescription)
                )
            }
            IconButton(onClick = { onDeleteCategory(category) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.actionCategoriesScreen_deleteCategory_contentDescription)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCategoriesScreenTopAppBar(
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.actionCategoriesScreen_title)
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
private fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onSave: (name: String, color: PastelAccentColor) -> Unit
) {
    var categoryName by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf<PastelAccentColor?>(null) }
    val isDarkTheme = isSystemInDarkTheme()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.actionCategoriesScreen_addCategoryDialog_title),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text(stringResource(R.string.actionCategoriesScreen_addCategoryDialog_categoryName_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Text(
                    text = stringResource(R.string.actionCategoriesScreen_addCategoryDialog_selectColor_label),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                ColorSelectionGrid(
                    selectedColor = selectedColor,
                    onColorSelected = { selectedColor = it },
                    isDarkTheme = isDarkTheme
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { 
                    if (selectedColor != null) {
                        onSave(categoryName.trim(), selectedColor!!)
                    }
                },
                enabled = categoryName.trim().isNotEmpty() && selectedColor != null
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
private fun EditCategoryDialog(
    category: ActionCategoryItem,
    onDismiss: () -> Unit,
    onSave: (name: String, color: PastelAccentColor) -> Unit
) {
    var categoryName by remember { mutableStateOf(category.name) }
    var selectedColor by remember { mutableStateOf<PastelAccentColor?>(category.color) }
    val isDarkTheme = isSystemInDarkTheme()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.actionCategoriesScreen_editCategoryDialog_title),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text(stringResource(R.string.actionCategoriesScreen_editCategoryDialog_categoryName_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Text(
                    text = stringResource(R.string.actionCategoriesScreen_editCategoryDialog_selectColor_label),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                ColorSelectionGrid(
                    selectedColor = selectedColor,
                    onColorSelected = { selectedColor = it },
                    isDarkTheme = isDarkTheme
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { 
                    if (selectedColor != null) {
                        onSave(categoryName.trim(), selectedColor!!)
                    }
                },
                enabled = categoryName.trim().isNotEmpty() && selectedColor != null && 
                         (categoryName.trim() != category.name || selectedColor != category.color)
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