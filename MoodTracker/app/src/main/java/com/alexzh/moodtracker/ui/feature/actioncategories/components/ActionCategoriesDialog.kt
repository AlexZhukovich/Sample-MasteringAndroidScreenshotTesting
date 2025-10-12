package com.alexzh.moodtracker.ui.feature.actioncategories.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.ui.model.ActionItem

@Composable
fun AddActionDialog(
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

@Composable
fun EditActionDialog(
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
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onSave: (name: String) -> Unit
) {
    var categoryName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.actionCategoriesScreen_addCategoryDialog_title),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            OutlinedTextField(
                value = categoryName,
                onValueChange = { categoryName = it },
                label = { Text(stringResource(R.string.actionCategoriesScreen_addCategoryDialog_categoryName_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(categoryName.trim()) },
                enabled = categoryName.trim().isNotEmpty()
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
fun EditCategoryDialog(
    category: ActionCategoryItem,
    onDismiss: () -> Unit,
    onSave: (name: String) -> Unit
) {
    var categoryName by remember { mutableStateOf(category.name) }

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
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(categoryName.trim()) },
                enabled = categoryName.trim().isNotEmpty() && categoryName.trim() != category.name
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