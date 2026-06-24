package com.alexzh.moodtracker.actionmanagement.components

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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.alexzh.designsystem.component.dialog.FormDialog
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.actionmanagement.R
import com.alexzh.moodtracker.common.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.github.takahirom.roborazzi.annotations.ManualClockOptions
import com.github.takahirom.roborazzi.annotations.RoboComposePreviewOptions

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

/**
 * The `@RoboComposePreviewOptions` annotation is used to configure how ComposablePreviewScanner
 * captures `@Preview` functions with Roborazzi.
 *
 * Using `manualClockOptions` with `advanceTimeMillis = 0L` sets the virtual clock at the initial frame,
 * preventing endless animations (such as a blinking cursor) from running indefinitely.
 *
 * See the "screenshot-tests-compose-preview-scanner-roborazzi" module.
 */
@RoboComposePreviewOptions(
    manualClockOptions = [ManualClockOptions(advanceTimeMillis = 0L)]
)
@PreviewLightDark
@Composable
fun Preview_AddActionDialog() {
    AppTheme {
        AddActionDialog(
            onDismiss = {},
            onSave = {}
        )
    }
}

/**
 * The `@RoboComposePreviewOptions` annotation is used to configure how ComposablePreviewScanner
 * captures `@Preview` functions with Roborazzi.
 *
 * Using `manualClockOptions` with `advanceTimeMillis = 0L` sets the virtual clock at the initial frame,
 * preventing endless animations (such as a blinking cursor) from running indefinitely.
 *
 * See the "screenshot-tests-compose-preview-scanner-roborazzi" module.
 */
@RoboComposePreviewOptions(
    manualClockOptions = [ManualClockOptions(advanceTimeMillis = 0L)]
)
@PreviewLightDark
@Composable
fun Preview_EditActionDialog() {
    AppTheme {
        EditActionDialog(
            action = ActionItem(id = 1L, name = "Running"),
            onDismiss = {},
            onSave = { _, _ -> }
        )
    }
}

/**
 * The `@RoboComposePreviewOptions` annotation is used to configure how ComposablePreviewScanner
 * captures `@Preview` functions with Roborazzi.
 *
 * Using `manualClockOptions` with `advanceTimeMillis = 0L` sets the virtual clock at the initial frame,
 * preventing endless animations (such as a blinking cursor) from running indefinitely.
 *
 * See the "screenshot-tests-compose-preview-scanner-roborazzi" module.
 */
@RoboComposePreviewOptions(
    manualClockOptions = [ManualClockOptions(advanceTimeMillis = 0L)]
)
@PreviewLightDark
@Composable
fun Preview_AddCategoryDialog() {
    AppTheme {
        AddCategoryDialog(
            onDismiss = {},
            onSave = {}
        )
    }
}

/**
 * The `@RoboComposePreviewOptions` annotation is used to configure how ComposablePreviewScanner
 * captures `@Preview` functions with Roborazzi.
 *
 * Using `manualClockOptions` with `advanceTimeMillis = 0L` sets the virtual clock at the initial frame,
 * preventing endless animations (such as a blinking cursor) from running indefinitely.
 *
 * See the "screenshot-tests-compose-preview-scanner-roborazzi" module.
 */
@RoboComposePreviewOptions(
    manualClockOptions = [ManualClockOptions(advanceTimeMillis = 0L)]
)
@PreviewLightDark
@Composable
fun Preview_EditCategoryDialog() {
    AppTheme {
        EditCategoryDialog(
            category = ActionCategoryItem(id = 1L, name = "Exercise"),
            onDismiss = {},
            onSave = {}
        )
    }
}