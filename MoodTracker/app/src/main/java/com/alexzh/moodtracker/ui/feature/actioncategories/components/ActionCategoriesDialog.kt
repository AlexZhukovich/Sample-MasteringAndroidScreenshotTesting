package com.alexzh.moodtracker.ui.feature.actioncategories.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.dialog.FormDialog
import com.alexzh.moodtracker.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.ui.model.ActionItem

@Composable
fun AddActionDialog(
    onDismiss: () -> Unit,
    onSave: (actionName: String) -> Unit
) {
    var actionName by remember { mutableStateOf("") }

    FormDialog(
        title = stringResource(R.string.actionCategoryDetailsScreen_addActionDialog_title),
        onDismiss = onDismiss,
        onConfirm = { onSave(actionName.trim()) },
        confirmButtonText = stringResource(R.string.common_save_label),
        confirmButtonEnabled = actionName.trim().isNotEmpty()
    ) {
        OutlinedTextField(
            value = actionName,
            onValueChange = { actionName = it },
            label = { Text(stringResource(R.string.actionCategoryDetailsScreen_addActionDialog_actionName_label)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

@Composable
fun EditActionDialog(
    action: ActionItem,
    onDismiss: () -> Unit,
    onSave: (actionId: Long, actionName: String) -> Unit
) {
    var actionName by remember { mutableStateOf(action.name) }

    FormDialog(
        title = stringResource(R.string.actionCategoryDetailsScreen_editActionDialog_title),
        onDismiss = onDismiss,
        onConfirm = { onSave(action.id, actionName.trim()) },
        confirmButtonText = stringResource(R.string.common_save_label),
        confirmButtonEnabled = actionName.trim().isNotEmpty() && actionName.trim() != action.name
    ) {
        OutlinedTextField(
            value = actionName,
            onValueChange = { actionName = it },
            label = { Text(stringResource(R.string.actionCategoryDetailsScreen_editActionDialog_actionName_label)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onSave: (name: String) -> Unit
) {
    var categoryName by remember { mutableStateOf("") }

    FormDialog(
        title = stringResource(R.string.actionCategoriesScreen_addCategoryDialog_title),
        onDismiss = onDismiss,
        onConfirm = { onSave(categoryName.trim()) },
        confirmButtonText = stringResource(R.string.common_save_label),
        confirmButtonEnabled = categoryName.trim().isNotEmpty()
    ) {
        OutlinedTextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            label = { Text(stringResource(R.string.actionCategoriesScreen_addCategoryDialog_categoryName_label)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

@Composable
fun EditCategoryDialog(
    category: ActionCategoryItem,
    onDismiss: () -> Unit,
    onSave: (name: String) -> Unit
) {
    var categoryName by remember { mutableStateOf(category.name) }

    FormDialog(
        title = stringResource(R.string.actionCategoriesScreen_editCategoryDialog_title),
        onDismiss = onDismiss,
        onConfirm = { onSave(categoryName.trim()) },
        confirmButtonText = stringResource(R.string.common_save_label),
        confirmButtonEnabled = categoryName.trim().isNotEmpty() && categoryName.trim() != category.name
    ) {
        OutlinedTextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            label = { Text(stringResource(R.string.actionCategoriesScreen_editCategoryDialog_categoryName_label)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}