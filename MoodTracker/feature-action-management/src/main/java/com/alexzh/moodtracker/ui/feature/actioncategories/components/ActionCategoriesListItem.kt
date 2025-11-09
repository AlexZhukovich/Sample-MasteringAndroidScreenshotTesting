package com.alexzh.moodtracker.ui.feature.actioncategories.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.component.button.IconButton
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.icon.DeleteIcon
import com.alexzh.designsystem.icon.EditIcon
import com.alexzh.moodtracker.feature.actionmanagement.R
import com.alexzh.moodtracker.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.ui.model.ActionItem

@Composable
fun ActionItemCard(
    action: ActionItem,
    onEditAction: () -> Unit,
    onDeleteAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = action.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            Row {
                IconButton(
                    onClick = onEditAction,
                    icon = EditIcon,
                    contentDescription = stringResource(R.string.actionCategoryDetailsScreen_editAction_contentDescription)
                )
                IconButton(
                    onClick = onDeleteAction,
                    icon = DeleteIcon,
                    contentDescription = stringResource(R.string.actionCategoryDetailsScreen_deleteAction_contentDescription)
                )
            }
        }
    }
}

@Composable
fun ActionCategoryCard(
    modifier: Modifier = Modifier,
    category: ActionCategoryItem,
    isSelected: Boolean = false,
    onActionCategoryClick: (Long) -> Unit,
    onEditCategory: (ActionCategoryItem) -> Unit,
    onDeleteCategory: (ActionCategoryItem) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    )
                } else {
                    Modifier
                }
            ),
        onClick = { onActionCategoryClick(category.id) }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.weight(1.0f)
                    .padding(start = 4.dp),
                text = category.name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(
                onClick = { onEditCategory(category) },
                icon = EditIcon,
                contentDescription = stringResource(R.string.actionCategoriesScreen_editCategory_contentDescription)
            )
            IconButton(
                onClick = { onDeleteCategory(category) },
                icon = DeleteIcon,
                contentDescription = stringResource(R.string.actionCategoriesScreen_deleteCategory_contentDescription)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ActionItemCardPreview() {
    val action = ActionItem(
        id = 1L,
        name = "Running"
    )

    AppTheme {
        ActionItemCard(
            action = action,
            onEditAction = {},
            onDeleteAction = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_ActionCategoryCard_Unselected() {
    val category = ActionCategoryItem(
        id = 1L,
        name = "Exercise"
    )

    AppTheme {
        ActionCategoryCard(
            category = category,
            isSelected = false,
            onActionCategoryClick = {},
            onEditCategory = {},
            onDeleteCategory = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_ActionCategoryCard_Selected() {
    val category = ActionCategoryItem(
        id = 1L,
        name = "Exercise"
    )

    AppTheme {
        ActionCategoryCard(
            category = category,
            isSelected = true,
            onActionCategoryClick = {},
            onEditCategory = {},
            onDeleteCategory = {}
        )
    }
}