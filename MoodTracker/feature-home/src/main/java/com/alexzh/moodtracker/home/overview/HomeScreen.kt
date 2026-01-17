package com.alexzh.moodtracker.home.overview

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldDefaults
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirectiveWithTwoPanesOnMediumWidth
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.alexzh.designsystem.component.bars.TopAppBarAction
import com.alexzh.designsystem.component.bars.TopAppBarWithBackButton
import com.alexzh.designsystem.component.button.PrimaryIconButton
import com.alexzh.designsystem.component.dialog.DeleteConfirmationDialog
import com.alexzh.designsystem.component.empty.EmptyState
import com.alexzh.designsystem.component.media.AsyncImage
import com.alexzh.designsystem.component.navigation.AppNavigationSuiteScaffold
import com.alexzh.designsystem.component.selector.daterangeselector.DateRangeSelector
import com.alexzh.designsystem.component.selector.daterangeselector.rememberDateRangeSelectorState
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.icon.AddIcon
import com.alexzh.designsystem.icon.DeleteIcon
import com.alexzh.designsystem.icon.EditIcon
import com.alexzh.moodtracker.common.ui.model.ActionItem
import com.alexzh.moodtracker.common.ui.model.LocalizedMood
import com.alexzh.moodtracker.common.ui.navigation.defaultBottomNavigationItems
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.moodtracker.home.R
import com.alexzh.moodtracker.home.model.MoodItem
import com.alexzh.moodtracker.home.overview.components.MoodActionChips
import com.alexzh.moodtracker.home.overview.components.MoodItemCard
import com.alexzh.moodtracker.home.overview.components.MoodPreviewHeader
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    onNavigateToEditMood: (Long) -> Unit,
    onNavigateToAddMood: () -> Unit,
    onNavigateToStatistics: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onNavigateToEditMood = onNavigateToEditMood,
        onNavigateToAddMood = onNavigateToAddMood,
        onChangeSelectedDate = { viewModel.onEvent(HomeScreenEvent.OnChangeData(it)) },
        onSelectMoodItem = { viewModel.onEvent(HomeScreenEvent.OnSelectMoodItem(it)) },
        onClearSelection = { viewModel.onEvent(HomeScreenEvent.OnClearSelection) },
        onDeleteMood = { viewModel.onEvent(HomeScreenEvent.OnDeleteMood) },
        onNavigateToStatistics = onNavigateToStatistics,
        onNavigateToSettings = onNavigateToSettings,
    )
}

@OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3AdaptiveApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun HomeScreenContent(
    uiState: HomeScreenUiState,
    onNavigateToEditMood: (Long) -> Unit,
    onNavigateToAddMood: () -> Unit,
    onChangeSelectedDate: (LocalDate) -> Unit,
    onSelectMoodItem: (Long) -> Unit,
    onClearSelection: () -> Unit,
    onDeleteMood: () -> Unit,
    onNavigateToStatistics: () -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    val onShowDeleteConfirmationDialog = { showDeleteConfirmationDialog = true }
    val onHideDeleteConfirmationDialog = { showDeleteConfirmationDialog = false }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>(
        scaffoldDirective = calculatePaneScaffoldDirectiveWithTwoPanesOnMediumWidth(currentWindowAdaptiveInfo()),
        adaptStrategies = ListDetailPaneScaffoldDefaults.adaptStrategies(),
    )

    LaunchedEffect(uiState.selectedMoodItem) {
        if (uiState.selectedMoodItem != null) {
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, uiState.selectedMoodItem.id)
        } else {
            navigator.navigateTo(ListDetailPaneScaffoldRole.List)
        }
    }

    if (showDeleteConfirmationDialog) {
        DeleteConfirmationDialog(
            title = stringResource(R.string.homeScreen_deleteDialogTitle_label),
            text = stringResource(R.string.homeScreen_deleteDialogText_label),
            onDismiss = { onHideDeleteConfirmationDialog() },
            onConfirm = {
                onHideDeleteConfirmationDialog()
                onDeleteMood()
            }
        )
    }

    when {
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) -> {
            AppNavigationSuiteScaffold(
                items = defaultBottomNavigationItems(
                    onNavigateToHome = { },
                    onNavigateToStatistics = onNavigateToStatistics,
                    onNavigateToSettings = onNavigateToSettings
                ),
                selectedItemIndex = 0
            ) {
                HomeScreenContentExpanded(
                    uiState = uiState,
                    navigator = navigator,
                    onNavigateToEditMood = onNavigateToEditMood,
                    onNavigateToAddMood = onNavigateToAddMood,
                    onChangeSelectedDate = onChangeSelectedDate,
                    onSelectMoodItem = onSelectMoodItem,
                    onClearSelection = onClearSelection,
                    onShowDeleteConfirmationDialog = onShowDeleteConfirmationDialog
                )
            }
        }
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> {
            if (uiState.selectedMoodItem != null) {
                val bottomSheetState = rememberModalBottomSheetState()
                val coroutineScope = rememberCoroutineScope()

                ModalBottomSheet(
                    onDismissRequest = onClearSelection,
                    sheetState = bottomSheetState
                ) {
                    MoodPreviewContentMediumExpanded(
                        moodItem = uiState.selectedMoodItem,
                        iconShape = uiState.iconShape,
                        isLayoutExpanded = false,
                        onClose = onClearSelection,
                        onNavigateToEditMood = { moodId ->
                            coroutineScope.launch {
                                bottomSheetState.hide()
                                onNavigateToEditMood(moodId)
                            }
                        },
                        onShowDeleteConfirmationDialog = onShowDeleteConfirmationDialog
                    )
                }
            }

            AppNavigationSuiteScaffold(
                items = defaultBottomNavigationItems(
                    onNavigateToHome = { },
                    onNavigateToStatistics = onNavigateToStatistics,
                    onNavigateToSettings = onNavigateToSettings
                ),
                selectedItemIndex = 0
            ) {
                HomeScreenContentCompactMedium(
                    uiState = uiState,
                    onNavigateToAddMood = onNavigateToAddMood,
                    onChangeSelectedDate = onChangeSelectedDate,
                    onSelectMoodItem = onSelectMoodItem
                )
            }
        }
        else -> {
            if (uiState.selectedMoodItem != null) {
                MoodPreviewScreenContentCompact(
                    moodItem = uiState.selectedMoodItem,
                    iconShape = uiState.iconShape,
                    onNavigateToEditMood = onNavigateToEditMood,
                    onNavigateUp = onClearSelection,
                    onShowDeleteConfirmationDialog = onShowDeleteConfirmationDialog
                )
            } else {
                AppNavigationSuiteScaffold(
                    items = defaultBottomNavigationItems(
                        onNavigateToHome = { },
                        onNavigateToStatistics = onNavigateToStatistics,
                        onNavigateToSettings = onNavigateToSettings
                    ),
                    selectedItemIndex = 0
                ) {
                    HomeScreenContentCompactMedium(
                        uiState = uiState,
                        onNavigateToAddMood = onNavigateToAddMood,
                        onChangeSelectedDate = onChangeSelectedDate,
                        onSelectMoodItem = onSelectMoodItem
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContentCompactMedium(
    uiState: HomeScreenUiState,
    onNavigateToAddMood: () -> Unit,
    onChangeSelectedDate: (LocalDate) -> Unit,
    onSelectMoodItem: (Long) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddMood) {
                Icon(
                    imageVector = AddIcon,
                    contentDescription = stringResource(R.string.homeScreen_addMoodButton_label)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val dateRangeSelectorState = rememberDateRangeSelectorState(
                selectedDate = uiState.selectedDate,
                daysCount = 7,
                currentDate = uiState.currentDate
            )

            DateRangeSelector(
                state = dateRangeSelectorState,
                onDateChange = onChangeSelectedDate
            )

            Box(modifier = Modifier.fillMaxSize()) {
                MoodItemsContent(
                    uiState = uiState,
                    onSelectMoodItem = onSelectMoodItem
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun HomeScreenContentExpanded(
    uiState: HomeScreenUiState,
    navigator: ThreePaneScaffoldNavigator<Long>,
    onNavigateToEditMood: (Long) -> Unit,
    onNavigateToAddMood: () -> Unit,
    onChangeSelectedDate: (LocalDate) -> Unit,
    onSelectMoodItem: (Long) -> Unit,
    onClearSelection: () -> Unit,
    onShowDeleteConfirmationDialog: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            if (uiState.selectedMoodItem == null) {
                FloatingActionButton(onClick = onNavigateToAddMood) {
                    Icon(
                        imageVector = AddIcon,
                        contentDescription = stringResource(R.string.homeScreen_addMoodButton_label)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val daysCount = 14
            val dateRangeSelectorState = rememberDateRangeSelectorState(
                selectedDate = uiState.selectedDate,
                daysCount = daysCount,
                currentDate = uiState.currentDate
            )

            DateRangeSelector(
                state = dateRangeSelectorState,
                onDateChange = onChangeSelectedDate
            )

            ListDetailPaneScaffold(
                directive = navigator.scaffoldDirective,
                value = navigator.scaffoldValue,
                listPane = {
                    MoodItemsContent(
                        uiState = uiState,
                        onSelectMoodItem = onSelectMoodItem
                    )
                },
                detailPane = {
                    if (uiState.selectedMoodItem != null) {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            MoodPreviewContentMediumExpanded(
                                moodItem = uiState.selectedMoodItem,
                                iconShape = uiState.iconShape,
                                isLayoutExpanded = true,
                                containerPadding = PaddingValues(all = 16.dp),
                                onNavigateToEditMood = onNavigateToEditMood,
                                onClose = onClearSelection,
                                onShowDeleteConfirmationDialog = onShowDeleteConfirmationDialog
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun MoodItemsContent(
    uiState: HomeScreenUiState,
    onSelectMoodItem: (Long) -> Unit,
) {
    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        uiState.moodItems.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize()) {
                EmptyState(
                    title = stringResource(R.string.homeScreen_emptyState_title),
                    text = stringResource(
                        R.string.homeScreen_emptyState_label,
                        uiState.selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                    )
                )
            }
        }
        else -> {
            MoodItemsGrid(
                moodItems = uiState.moodItems,
                iconShape = uiState.iconShape,
                onMoodItemClick = onSelectMoodItem,
                selectedMoodId = uiState.selectedMoodItem?.id,
            )
        }
    }
}

@Composable
private fun MoodItemsGrid(
    modifier: Modifier = Modifier,
    moodItems: List<MoodItem>,
    iconShape: IconShape,
    onMoodItemClick: (Long) -> Unit,
    selectedMoodId: Long? = null,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(280.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(moodItems) { moodItem ->
            MoodItemCard(
                moodItem = moodItem,
                iconShape = iconShape,
                onClick = { onMoodItemClick(moodItem.id) },
                isSelected = selectedMoodId == moodItem.id
            )
        }
    }
}

@Composable
private fun MoodPreviewContentMediumExpanded(
    moodItem: MoodItem,
    iconShape: IconShape,
    isLayoutExpanded: Boolean,
    containerPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    onNavigateToEditMood: (Long) -> Unit,
    onClose: () -> Unit,
    onShowDeleteConfirmationDialog: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(containerPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MoodPreviewHeader(
            moodItem = moodItem,
            iconShape = iconShape,
            isLayoutExpanded = isLayoutExpanded,
            onClose = onClose,
            onNavigateToEditMood = onNavigateToEditMood,
            onDelete = onShowDeleteConfirmationDialog
        )

        if (moodItem.actions.isNotEmpty()) {
            MoodActionChips(actions = moodItem.actions)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (moodItem.note.isNotBlank()) {
                Text(
                    text = moodItem.note,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if (moodItem.photos.isNotEmpty()) {
                MoodPreviewPhotos(photos = moodItem.photos)
            }
        }

        if (isLayoutExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryIconButton(
                    onClick = { onNavigateToEditMood(moodItem.id) },
                    icon = EditIcon,
                    contentDescription = stringResource(R.string.homeScreenPreview_editMood_contentDescription)
                )

                PrimaryIconButton(
                    onClick = onShowDeleteConfirmationDialog,
                    icon = DeleteIcon,
                    contentDescription = stringResource(R.string.homeScreenPreview_deleteMood_contentDescription)
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoodPreviewScreenContentCompact(
    moodItem: MoodItem,
    iconShape: IconShape,
    onNavigateToEditMood: (Long) -> Unit,
    onNavigateUp: () -> Unit,
    onShowDeleteConfirmationDialog: () -> Unit
) {
    BackHandler(onBack = onNavigateUp)

    Scaffold(
        topBar = {
            TopAppBarWithBackButton(
                titleContent = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(moodItem.mood.getIcon(iconShape)),
                            contentDescription = stringResource(moodItem.mood.label)
                        )
                        Column {
                            Text(
                                text = stringResource(moodItem.mood.label),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = moodItem.formattedDate,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                onBack = onNavigateUp,
                actions = {
                    TopAppBarAction(
                        onClick = { onNavigateToEditMood(moodItem.id) },
                        icon = EditIcon,
                        contentDescription = stringResource(R.string.homeScreenPreview_editMood_contentDescription)
                    )
                    TopAppBarAction(
                        onClick = onShowDeleteConfirmationDialog,
                        icon = DeleteIcon,
                        contentDescription = stringResource(R.string.homeScreenPreview_deleteMood_contentDescription)
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (moodItem.actions.isNotEmpty()) {
                MoodActionChips(actions = moodItem.actions)
            }

            if (moodItem.note.isNotBlank()) {
                Text(
                    text = moodItem.note,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if (moodItem.photos.isNotEmpty()) {
                MoodPreviewPhotos(photos = moodItem.photos)
            }
        }
    }
}

@Composable
private fun MoodPreviewPhotos(
    photos: List<Uri>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        photos.forEach { imageUri ->
            AsyncImage(
                uri = imageUri,
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(device = PHONE, showBackground = true)
@Preview(device = PHONE, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(device = PIXEL_TABLET, showBackground = true)
@Preview(device = PIXEL_TABLET, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Preview_HomeScreen(
    @PreviewParameter(HomeScreenUiStateProvider ::class) uiState: HomeScreenUiState
) {
    AppTheme {
        HomeScreenContent(
            uiState = uiState,
            onNavigateToEditMood = { },
            onNavigateToAddMood = { },
            onChangeSelectedDate = { },
            onSelectMoodItem = { },
            onClearSelection = { },
            onDeleteMood = { },
            onNavigateToStatistics = { },
            onNavigateToSettings = { },
        )
    }
}

class HomeScreenUiStateProvider : PreviewParameterProvider<HomeScreenUiState> {
    override val values: Sequence<HomeScreenUiState>
        get() = sequenceOf(
            HomeScreenUiState(
                isLoading = true,
                selectedDate = LocalDate.of(2025, 1, 1),
                currentDate = LocalDate.of(2025, 1, 5)
            ),
            HomeScreenUiState(
                selectedDate = LocalDate.of(2025, 1, 1),
                currentDate = LocalDate.of(2025, 1, 5)
            ),
            HomeScreenUiState(
                moodItems = listOf(
                    MoodItem(
                        id = 1,
                        mood = LocalizedMood.HAPPY,
                        date = LocalDate.of(2025, 1, 5).atTime(19, 0),
                        note = "Had a great day at work!",
                        actions = listOf(
                            ActionItem(1, "Running"),
                            ActionItem(2, "Meditation"),
                            ActionItem(3, "Reading")
                        )
                    ),
                    MoodItem(
                        id = 2,
                        mood = LocalizedMood.OK,
                        date = LocalDate.of(2025, 1, 5).atTime(15, 45),
                        note = "",
                        actions = emptyList()
                    ),
                    MoodItem(
                        id = 3,
                        mood = LocalizedMood.SAD,
                        date = LocalDate.of(2025, 1, 5).atTime(11, 30),
                        note = "",
                        actions = listOf(
                            ActionItem(4, "Journaling"),
                            ActionItem(5, "Music")
                        )
                    ),
                ),
                selectedDate = LocalDate.of(2025, 1, 1),
                currentDate = LocalDate.of(2025, 1, 5)
            )
        )
}