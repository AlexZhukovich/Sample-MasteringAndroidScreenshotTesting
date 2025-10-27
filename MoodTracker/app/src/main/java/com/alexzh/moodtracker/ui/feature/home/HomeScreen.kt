package com.alexzh.moodtracker.ui.feature.home

import android.net.Uri
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldDefaults
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirectiveWithTwoPanesOnMediumWidth
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.domain.model.IconShape
import com.alexzh.moodtracker.ui.designsystem.bars.TopAppBar
import com.alexzh.moodtracker.ui.designsystem.bars.TopAppBarAction
import com.alexzh.moodtracker.ui.designsystem.button.IconButton
import com.alexzh.moodtracker.ui.designsystem.button.PrimaryIconButton
import com.alexzh.moodtracker.ui.designsystem.dialog.DeleteConfirmationDialog
import com.alexzh.moodtracker.ui.designsystem.empty.EmptyState
import com.alexzh.moodtracker.ui.designsystem.icon.MoodIcon
import com.alexzh.moodtracker.ui.designsystem.media.AsyncImage
import com.alexzh.moodtracker.ui.designsystem.selector.daterangeselector.DateRangeSelector
import com.alexzh.moodtracker.ui.designsystem.selector.daterangeselector.rememberDateRangeSelectorState
import com.alexzh.moodtracker.ui.feature.home.components.MoodActionChips
import com.alexzh.moodtracker.ui.feature.home.components.MoodItemCard
import com.alexzh.moodtracker.ui.feature.home.components.MoodPreviewHeader
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.model.MoodItem
import com.alexzh.moodtracker.ui.navigation.AppNavigationItems
import com.alexzh.moodtracker.ui.navigation.AppNavigationSuiteScaffold
import com.alexzh.moodtracker.ui.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    onNavigateToEditMood: (Long) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAddMood: () -> Unit,
    onNavigateToStatistics: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onNavigateToEditMood = onNavigateToEditMood,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToAddMood = onNavigateToAddMood,
        onChangeSelectedDate = { viewModel.onEvent(HomeScreenEvent.OnChangeData(it)) },
        onSelectMoodItem = { viewModel.onEvent(HomeScreenEvent.OnSelectMoodItem(it)) },
        onClearSelection = { viewModel.onEvent(HomeScreenEvent.OnClearSelection) },
        onDeleteMood = { viewModel.onEvent(HomeScreenEvent.OnDeleteMood) },
        onNavigateToStatistics = onNavigateToStatistics
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
    onNavigateToSettings: () -> Unit,
    onNavigateToAddMood: () -> Unit,
    onChangeSelectedDate: (LocalDate) -> Unit,
    onSelectMoodItem: (Long) -> Unit,
    onClearSelection: () -> Unit,
    onDeleteMood: () -> Unit,
    onNavigateToStatistics: () -> Unit
) {
    val context = LocalContext.current
    val windowSizeClass = calculateWindowSizeClass(context as android.app.Activity)
    val windowWidthSizeClass = windowSizeClass.widthSizeClass
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

    when(windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            if (uiState.selectedMoodItem != null) {
                MoodPreviewScreenContent(
                    moodItem = uiState.selectedMoodItem,
                    iconShape = uiState.iconShape,
                    onNavigateToEditMood = onNavigateToEditMood,
                    onNavigateUp = onClearSelection,
                    onDelete = onDeleteMood
                )
            } else {
                AppNavigationSuiteScaffold(
                    selectedItem = AppNavigationItems.HOME,
                    onNavigateToHome = { },
                    onNavigateToStatistics = onNavigateToStatistics
                ) {
                    HomeScreenContentCompactMedium(
                        uiState = uiState,
                        onNavigateToSettings = onNavigateToSettings,
                        onNavigateToAddMood = onNavigateToAddMood,
                        onChangeSelectedDate = onChangeSelectedDate,
                        onSelectMoodItem = onSelectMoodItem
                    )
                }
            }
        }
        WindowWidthSizeClass.Medium -> {
            if (uiState.selectedMoodItem != null) {
                val bottomSheetState = rememberModalBottomSheetState()
                val coroutineScope = rememberCoroutineScope()

                ModalBottomSheet(
                    onDismissRequest = onClearSelection,
                    sheetState = bottomSheetState
                ) {
                    MoodPreviewContent(
                        moodItem = uiState.selectedMoodItem,
                        iconShape = uiState.iconShape,
                        windowWidthSizeClass = windowWidthSizeClass,
                        onClose = onClearSelection,
                        onNavigateToEditMood = { moodId ->
                            coroutineScope.launch {
                                bottomSheetState.hide()
                                onNavigateToEditMood(moodId)
                            }
                        },
                        onDelete = onDeleteMood
                    )
                }
            }

            AppNavigationSuiteScaffold(
                selectedItem = AppNavigationItems.HOME,
                onNavigateToHome = { },
                onNavigateToStatistics = onNavigateToStatistics
            ) {
                HomeScreenContentCompactMedium(
                    uiState = uiState,
                    onNavigateToSettings = onNavigateToSettings,
                    onNavigateToAddMood = onNavigateToAddMood,
                    onChangeSelectedDate = onChangeSelectedDate,
                    onSelectMoodItem = onSelectMoodItem
                )
            }
        }
        else -> {
            AppNavigationSuiteScaffold(
                selectedItem = AppNavigationItems.HOME,
                onNavigateToHome = { },
                onNavigateToStatistics = onNavigateToStatistics
            ) {
                HomeScreenContentExpanded(
                    uiState = uiState,
                    windowWidthSizeClass = windowWidthSizeClass,
                    navigator = navigator,
                    onNavigateToEditMood = onNavigateToEditMood,
                    onNavigateToSettings = onNavigateToSettings,
                    onNavigateToAddMood = onNavigateToAddMood,
                    onChangeSelectedDate = onChangeSelectedDate,
                    onSelectMoodItem = onSelectMoodItem,
                    onClearSelection = onClearSelection,
                    onDeleteMood = onDeleteMood
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContentCompactMedium(
    uiState: HomeScreenUiState,
    onNavigateToSettings: () -> Unit,
    onNavigateToAddMood: () -> Unit,
    onChangeSelectedDate: (LocalDate) -> Unit,
    onSelectMoodItem: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.homeScreen_title),
                actions = {
                    TopAppBarAction(
                        icon = painterResource(R.drawable.ic_settings),
                        onClick = onNavigateToSettings,
                        contentDescription = stringResource(R.string.navigation_settings_label)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddMood) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
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
    windowWidthSizeClass: WindowWidthSizeClass,
    navigator: androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator<Long>,
    onNavigateToEditMood: (Long) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAddMood: () -> Unit,
    onChangeSelectedDate: (LocalDate) -> Unit,
    onSelectMoodItem: (Long) -> Unit,
    onClearSelection: () -> Unit,
    onDeleteMood: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.homeScreen_title),
                actions = {
                    TopAppBarAction(
                        icon = painterResource(R.drawable.ic_settings),
                        onClick = onNavigateToSettings,
                        contentDescription = stringResource(R.string.navigation_settings_label)
                    )
                }
            )
        },
        floatingActionButton = {
            if (uiState.selectedMoodItem == null) {
                FloatingActionButton(onClick = onNavigateToAddMood) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
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
            val daysCount = when (windowWidthSizeClass) {
                WindowWidthSizeClass.Compact -> 7
                WindowWidthSizeClass.Medium -> 10
                WindowWidthSizeClass.Expanded -> 14
                else -> 14
            }

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
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            MoodPreviewContent(
                                moodItem = uiState.selectedMoodItem,
                                iconShape = uiState.iconShape,
                                windowWidthSizeClass = windowWidthSizeClass,
                                onNavigateToEditMood = onNavigateToEditMood,
                                onClose = onClearSelection,
                                onDelete = onDeleteMood
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
private fun MoodPreviewContent(
    moodItem: MoodItem,
    iconShape: IconShape,
    windowWidthSizeClass: WindowWidthSizeClass,
    onNavigateToEditMood: (Long) -> Unit,
    onClose: () -> Unit,
    onDelete: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val containerPadding = if (windowWidthSizeClass == WindowWidthSizeClass.Compact || windowWidthSizeClass == WindowWidthSizeClass.Medium) {
        PaddingValues(horizontal = 16.dp)
    } else {
        PaddingValues(all = 16.dp)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(containerPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MoodPreviewHeader(
            moodItem = moodItem,
            iconShape = iconShape,
            windowWidthSizeClass = windowWidthSizeClass,
            onClose = onClose,
            onNavigateToEditMood = onNavigateToEditMood,
            onDelete = { showDeleteDialog = true }
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

        if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryIconButton(
                    onClick = { onNavigateToEditMood(moodItem.id) },
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = stringResource(R.string.homeScreenPreview_editMood_contentDescription)
                )

                PrimaryIconButton(
                    onClick = onDelete,
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.homeScreenPreview_deleteMood_contentDescription)
                )
            }
        }
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            title = stringResource(R.string.common_deleteDialogTitle_label),
            text = stringResource(R.string.common_deleteDialogText_label),
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                showDeleteDialog = false
                onDelete()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoodPreviewScreenContent(
    moodItem: MoodItem,
    iconShape: IconShape,
    onNavigateToEditMood: (Long) -> Unit,
    onNavigateUp: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    BackHandler(onBack = onNavigateUp)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MoodIcon(
                            modifier = Modifier.size(32.dp),
                            mood = moodItem.mood,
                            iconShape = iconShape
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
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp,
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = stringResource(R.string.common_navigateUp_contentDescription)
                    )
                },
                actions = {
                    IconButton(
                        onClick = { onNavigateToEditMood(moodItem.id) },
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = stringResource(R.string.homeScreenPreview_editMood_contentDescription)
                    )
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        painter = painterResource(R.drawable.ic_delete),
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

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            title = stringResource(R.string.common_deleteDialogTitle_label),
            text = stringResource(R.string.common_deleteDialogText_label),
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                showDeleteDialog = false
                onDelete()
            }
        )
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

@Preview(showBackground = true)
@Composable
private fun Preview_HomeScreen_Loading() {
    val selectedDate = LocalDate.of(2025, 1, 1)
    val currentDate = LocalDate.of(2025, 1, 5)
    val uiState = HomeScreenUiState(
        isLoading = true,
        selectedDate = selectedDate,
        currentDate = currentDate
    )

    AppTheme {
        HomeScreenContent(
            uiState = uiState,
            onNavigateToEditMood = { },
            onNavigateToSettings = { },
            onNavigateToAddMood = { },
            onChangeSelectedDate = { },
            onSelectMoodItem = { },
            onClearSelection = { },
            onDeleteMood = { },
            onNavigateToStatistics = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview_HomeScreen_Empty() {
    val selectedDate = LocalDate.of(2025, 1, 1)
    val currentDate = LocalDate.of(2025, 1, 5)
    val uiState = HomeScreenUiState(
        selectedDate = selectedDate,
        currentDate = currentDate
    )

    AppTheme {
        HomeScreenContent(
            uiState = uiState,
            onNavigateToEditMood = { },
            onNavigateToSettings = { },
            onNavigateToAddMood = { },
            onChangeSelectedDate = { },
            onSelectMoodItem = { },
            onClearSelection = { },
            onDeleteMood = { },
            onNavigateToStatistics = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview_HomeScreen_Success() {
    val selectedDate = LocalDate.of(2025, 1, 1)
    val currentDate = LocalDate.of(2025, 1, 5)
    val moodItems = listOf(
        MoodItem(
            id = 1,
            mood = LocalizedMood.HAPPY,
            date = currentDate.atTime(19, 0),
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
            date = currentDate.atTime(15, 45),
            note = "",
            actions = emptyList()
        ),
        MoodItem(
            id = 3,
            mood = LocalizedMood.SAD,
            date = currentDate.atTime(11, 30),
            note = "",
            actions = listOf(
                ActionItem(4, "Journaling"),
                ActionItem(5, "Music")
            )
        )
    )
    val uiState = HomeScreenUiState(
        moodItems = moodItems,
        selectedDate = selectedDate,
        currentDate = currentDate
    )

    AppTheme {
        HomeScreenContent(
            uiState = uiState,
            onNavigateToEditMood = { },
            onNavigateToSettings = { },
            onNavigateToAddMood = { },
            onChangeSelectedDate = { },
            onSelectMoodItem = { },
            onClearSelection = { },
            onDeleteMood = { },
            onNavigateToStatistics = { }
        )
    }
}