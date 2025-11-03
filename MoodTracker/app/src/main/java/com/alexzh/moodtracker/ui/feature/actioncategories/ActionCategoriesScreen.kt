package com.alexzh.moodtracker.ui.feature.actioncategories

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldDefaults
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirectiveWithTwoPanesOnMediumWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.designsystem.component.bars.TopAppBar
import com.alexzh.designsystem.component.bars.TopAppBarWithBackButton
import com.alexzh.designsystem.component.dialog.DeleteConfirmationDialog
import com.alexzh.designsystem.component.empty.EmptyState
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.R as DesignSystemR
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.feature.actioncategories.components.ActionCategoryCard
import com.alexzh.moodtracker.ui.feature.actioncategories.components.ActionItemCard
import com.alexzh.moodtracker.ui.feature.actioncategories.components.AddActionDialog
import com.alexzh.moodtracker.ui.feature.actioncategories.components.AddCategoryDialog
import com.alexzh.moodtracker.ui.feature.actioncategories.components.EditActionDialog
import com.alexzh.moodtracker.ui.feature.actioncategories.components.EditCategoryDialog
import com.alexzh.moodtracker.ui.model.ActionCategoryItem
import com.alexzh.moodtracker.ui.model.ActionItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ActionCategoriesScreen(
    viewModel: ActionCategoriesScreenViewModel,
    onNavigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ActionCategoriesScreenContent(
        uiState = uiState,
        onAddCategory = { name -> viewModel.onEvent(ActionCategoriesScreenEvent.OnAddCategory(name)) },
        onEditCategory = { categoryId, name -> viewModel.onEvent(ActionCategoriesScreenEvent.OnEditCategory(categoryId, name)) },
        onDeleteCategory = { categoryId -> viewModel.onEvent(ActionCategoriesScreenEvent.OnDeleteCategory(categoryId)) },
        onSelectCategory = { categoryId -> viewModel.onEvent(ActionCategoriesScreenEvent.OnSelectCategory(categoryId)) },
        onClearCategorySelection = { viewModel.onEvent(ActionCategoriesScreenEvent.OnClearCategorySelection) },
        onAddAction = { actionName -> viewModel.onEvent(ActionCategoriesScreenEvent.OnAddAction(actionName)) },
        onEditAction = { actionId, actionName -> viewModel.onEvent(ActionCategoriesScreenEvent.OnEditAction(actionId, actionName)) },
        onDeleteAction = { actionId -> viewModel.onEvent(ActionCategoriesScreenEvent.OnDeleteAction(actionId)) },
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ActionCategoriesScreenContent(
    uiState: ActionCategoriesScreenUiState,
    onAddCategory: (name: String) -> Unit,
    onEditCategory: (categoryId: Long, name: String) -> Unit,
    onDeleteCategory: (categoryId: Long) -> Unit,
    onSelectCategory: (categoryId: Long) -> Unit,
    onClearCategorySelection: () -> Unit,
    onAddAction: (actionName: String) -> Unit,
    onEditAction: (actionId: Long, actionName: String) -> Unit,
    onDeleteAction: (actionId: Long) -> Unit,
    onNavigateUp: () -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>(
        scaffoldDirective = calculatePaneScaffoldDirectiveWithTwoPanesOnMediumWidth(currentWindowAdaptiveInfo()),
        adaptStrategies = ListDetailPaneScaffoldDefaults.adaptStrategies(),
    )

    val coroutineScope = rememberCoroutineScope()
    val isExpandedLayout = navigator.scaffoldDirective.maxHorizontalPartitions > 1

    BackHandler(enabled = !isExpandedLayout && uiState.selectedCategoryDetails != null) {
        onClearCategorySelection()
        coroutineScope.launch {
            navigator.navigateBack()
        }
    }
    
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    var editingCategory by remember { mutableStateOf<ActionCategoryItem?>(null) }
    var deletingCategory by remember { mutableStateOf<ActionCategoryItem?>(null) }

    editingCategory?.let { category ->
        EditCategoryDialog(
            category = category,
            onDismiss = { editingCategory = null },
            onSave = { name ->
                onEditCategory(category.id, name)
                editingCategory = null
            }
        )
    }

    deletingCategory?.let { category ->
        DeleteConfirmationDialog(
            title = stringResource(R.string.actionCategoriesScreen_deleteCategoryDialog_title),
            text = stringResource(R.string.actionCategoriesScreen_deleteCategoryDialog_label, category.name),
            onDismiss = { deletingCategory = null },
            onConfirm = {
                onDeleteCategory(category.id)
                deletingCategory = null
            }
        )
    }

    if (showAddCategoryDialog) {
        AddCategoryDialog(
            onDismiss = { showAddCategoryDialog = false },
            onSave = { name ->
                onAddCategory(name)
                showAddCategoryDialog = false
            }
        )
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                ActionCategoriesListPane(
                    isLoading = uiState.isLoadingCategories,
                    categories = uiState.categories,
                    selectedCategoryId = uiState.selectedCategoryDetails?.category?.id,
                    isExpandedLayout = navigator.scaffoldDirective.maxHorizontalPartitions > 1,
                    onCategoryClick = { categoryId ->
                        onSelectCategory(categoryId)
                        coroutineScope.launch {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, categoryId)
                        }
                    },
                    onEditCategory = { category -> editingCategory = category },
                    onDeleteCategory = { category -> deletingCategory = category },
                    onAddCategory = { showAddCategoryDialog = true },
                    onNavigateUp = onNavigateUp
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val coroutineScope = rememberCoroutineScope()
                uiState.selectedCategoryDetails?.let { categoryDetails ->
                    ActionCategoryDetailsPane(
                        categoryDetails = categoryDetails,
                        isExpandedLayout = isExpandedLayout,
                        onAddAction = onAddAction,
                        onEditAction = onEditAction,
                        onDeleteAction = onDeleteAction,
                        onNavigateUp = {
                            if (isExpandedLayout) {
                                onClearCategorySelection()
                            } else {
                                onClearCategorySelection()
                                coroutineScope.launch {
                                    navigator.navigateBack()
                                }
                            }
                        }
                    )
                } ?: ActionCategorySelectionPlaceholder()
            }
        }
    )
}

@Composable
private fun ActionCategorySelectionPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.actionCategoriesScreen_placeholder_label),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ActionCategoriesListPane(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    categories: List<ActionCategoryItem>,
    selectedCategoryId: Long?,
    isExpandedLayout: Boolean,
    onCategoryClick: (Long) -> Unit,
    onEditCategory: (ActionCategoryItem) -> Unit,
    onDeleteCategory: (ActionCategoryItem) -> Unit,
    onAddCategory: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBarWithBackButton(
                title = stringResource(R.string.actionCategoriesScreen_title),
                onBack = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCategory) {
                Icon(
                    painter = painterResource(DesignSystemR.drawable.ic_add),
                    contentDescription = stringResource(R.string.actionCategoriesScreen_addCategoryButton_label)
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
                categories.isEmpty() && isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                categories.isEmpty() -> EmptyState(
                    text = stringResource(R.string.actionCategoriesScreen_emptyState_label)
                )
                else -> ActionCategoriesList(
                    categories = categories,
                    selectedCategoryId = selectedCategoryId,
                    isExpandedLayout = isExpandedLayout,
                    onActionCategoryClick = onCategoryClick,
                    onEditCategory = onEditCategory,
                    onDeleteCategory = onDeleteCategory
                )
            }
        }
    }
}

@Composable
private fun ActionCategoryDetailsPane(
    modifier: Modifier = Modifier,
    categoryDetails: CategoryDetailsUiState,
    isExpandedLayout: Boolean,
    onAddAction: (String) -> Unit,
    onEditAction: (Long, String) -> Unit,
    onDeleteAction: (Long) -> Unit,
    onNavigateUp: () -> Unit
) {
    var editingAction by remember { mutableStateOf<ActionItem?>(null) }
    var deletingAction by remember { mutableStateOf<ActionItem?>(null) }
    var showAddActionDialog by remember { mutableStateOf(false) }

    editingAction?.let { action ->
        EditActionDialog(
            action = action,
            onDismiss = { editingAction = null },
            onSave = { actionId, actionName ->
                onEditAction(actionId, actionName)
                editingAction = null
            }
        )
    }

    deletingAction?.let { action ->
        DeleteConfirmationDialog(
            title = stringResource(R.string.actionCategoryDetailsScreen_deleteActionDialog_title),
            text = stringResource(R.string.actionCategoryDetailsScreen_deleteActionDialog_label, action.name),
            onDismiss = { deletingAction = null },
            onConfirm = {
                onDeleteAction(action.id)
                deletingAction = null
            }
        )
    }

    if (showAddActionDialog) {
        AddActionDialog(
            onDismiss = { showAddActionDialog = false },
            onSave = { actionName ->
                onAddAction(actionName)
                showAddActionDialog = false
            }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (!isExpandedLayout) {
                TopAppBarWithBackButton(
                    title = categoryDetails.category.name,
                    onBack = onNavigateUp
                )
            } else {
                TopAppBar(title = categoryDetails.category.name)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddActionDialog = true }) {
                Icon(
                    painter = painterResource(DesignSystemR.drawable.ic_add),
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
                categoryDetails.isLoadingActions -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                categoryDetails.actions.isEmpty() -> EmptyState(
                    text = stringResource(R.string.actionCategoryDetailsScreen_emptyState_label)
                )
                else -> ActionList(
                    actions = categoryDetails.actions,
                    onEditAction = { action -> editingAction = action },
                    onDeleteAction = { action -> deletingAction = action }
                )
            }
        }
    }
}

@Composable
private fun ActionCategoriesList(
    modifier: Modifier = Modifier,
    categories: List<ActionCategoryItem>,
    selectedCategoryId: Long?,
    isExpandedLayout: Boolean,
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
                isSelected = isExpandedLayout && selectedCategoryId == category.id,
                onActionCategoryClick = onActionCategoryClick,
                onEditCategory = onEditCategory,
                onDeleteCategory = onDeleteCategory
            )
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
            ActionItemCard(
                action = action,
                onEditAction = { onEditAction(action) },
                onDeleteAction = { onDeleteAction(action) }
            )
        }
    }
}

@Preview(name = "Phone - Light", device = PHONE, showBackground = true)
@Preview(name = "Phone - Dark", device = PHONE, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Tablet - Light", device = PIXEL_TABLET, showBackground = true)
@Preview(name = "Tablet - Dark", device = PIXEL_TABLET, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview_ActionCategoriesAdaptiveScreenContentTablet(
    @PreviewParameter(ActionCategoriesScreenUiStateProvider::class) uiState: ActionCategoriesScreenUiState
) {
    AppTheme {
        ActionCategoriesScreenContent(
            uiState = uiState,
            onAddCategory = { _ -> },
            onEditCategory = { _, _ -> },
            onDeleteCategory = { _ -> },
            onSelectCategory = { _ -> },
            onClearCategorySelection = { },
            onAddAction = { _ -> },
            onEditAction = { _, _ -> },
            onDeleteAction = { _ -> },
            onNavigateUp = { }
        )
    }
}

private val sampleCategories = listOf(
    ActionCategoryItem(id = 1L, name = "Hobbies"),
    ActionCategoryItem(id = 2L, name = "Physical Activity"),
    ActionCategoryItem(id = 3L, name = "Relaxation"),
    ActionCategoryItem(id = 4L, name = "Social"),
    ActionCategoryItem(id = 5L, name = "Work")
)

private val sampleActions = listOf(
    ActionItem(id = 1L, name = "Drawing"),
    ActionItem(id = 2L, name = "Writing"),
    ActionItem(id = 3L, name = "Cooking"),
    ActionItem(id = 4L, name = "Reading"),
    ActionItem(id = 5L, name = "Gaming")
)

class ActionCategoriesScreenUiStateProvider : PreviewParameterProvider<ActionCategoriesScreenUiState> {
    override val values: Sequence<ActionCategoriesScreenUiState>
        get() = sequenceOf(
            ActionCategoriesScreenUiState(
                categories = emptyList(),
                isLoadingCategories = false
            ),
            ActionCategoriesScreenUiState(
                categories = sampleCategories,
                isLoadingCategories = false
            ),
            ActionCategoriesScreenUiState(
                categories = sampleCategories,
                isLoadingCategories = false,
                selectedCategoryDetails = CategoryDetailsUiState(
                    category = sampleCategories.first(),
                    actions = sampleActions,
                    isLoadingActions = false
                )
            )
        )
}