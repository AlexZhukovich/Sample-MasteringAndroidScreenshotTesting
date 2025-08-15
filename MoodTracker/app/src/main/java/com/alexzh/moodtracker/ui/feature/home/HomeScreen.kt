package com.alexzh.moodtracker.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.designsystem.chip.Chip
import com.alexzh.moodtracker.ui.designsystem.empty.EmptyState
import com.alexzh.moodtracker.ui.designsystem.selector.daterangeselector.DateRangeSelector
import com.alexzh.moodtracker.ui.designsystem.selector.daterangeselector.rememberDateRangeSelectorState
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.model.LocalizedMood
import com.alexzh.moodtracker.ui.model.MoodItem
import com.alexzh.moodtracker.ui.navigation.AppBottomNavigationBar
import com.alexzh.moodtracker.ui.navigation.BottomNavigationItems
import com.alexzh.moodtracker.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    onNavigateToMoodPreview: (Long) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAddMood: () -> Unit,
    onNavigateToStatistics: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onNavigateToMoodPreview = onNavigateToMoodPreview,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToAddMood = onNavigateToAddMood,
        onChangeSelectedDate = { viewModel.onEvent(HomeScreenEvent.OnChangeData(it)) },
        onNavigateToStatistics = onNavigateToStatistics
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeScreenUiState,
    onNavigateToMoodPreview: (Long) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAddMood: () -> Unit,
    onChangeSelectedDate: (LocalDate) -> Unit,
    onNavigateToStatistics: () -> Unit,
) {
    Scaffold(
        topBar = {
            HomeScreenTopAppBar(
                onNavigateToSettings = onNavigateToSettings
            )
        },
        bottomBar = {
            AppBottomNavigationBar(
                selectedItem = BottomNavigationItems.HOME,
                onNavigateToHome = {},
                onNavigateToStatistics = onNavigateToStatistics
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddMood
            ) {
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

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    uiState.isLoading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                    uiState.moodItems.isEmpty() -> EmptyState(
                        title = stringResource(R.string.homeScreen_emptyState_title),
                        text = stringResource(
                            R.string.homeScreen_emptyState_label,
                            uiState.selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                        )
                    )
                    else -> MoodItemsList(uiState.moodItems, onNavigateToMoodPreview)
                }
            }
        }
    }
}

@Composable
private fun MoodItemsList(
    moodItems: List<MoodItem>,
    onMoodItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(moodItems) { moodItem ->
            MoodItemCard(
                moodItem = moodItem,
                onClick = { onMoodItemClick(moodItem.id) }
            )
        }
    }
}

@Composable
private fun MoodItemCard(
    moodItem: MoodItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        modifier = Modifier.size(42.dp),
                        painter = painterResource(moodItem.mood.icon),
                        contentDescription = stringResource(moodItem.mood.label),
                    )
                    Text(
                        text = stringResource(moodItem.mood.label),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Text(
                    text = moodItem.formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (moodItem.note.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = moodItem.note,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            if (moodItem.actions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                ActionChips(actions = moodItem.actions)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ActionChips(
    actions: List<ActionItem>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        actions.forEach { action ->
            Chip(text = action.name)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateToSettings: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(stringResource(R.string.homeScreen_title)) },
        actions = {
            IconButton(onClick = onNavigateToSettings) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = stringResource(R.string.navigation_settings_label)
                )
            }

        }
    )
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
            onNavigateToMoodPreview = { },
            onNavigateToSettings = { },
            onNavigateToAddMood = { },
            onChangeSelectedDate = { },
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
            onNavigateToMoodPreview = { },
            onNavigateToSettings = { },
            onNavigateToAddMood = { },
            onChangeSelectedDate = { },
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
            onNavigateToMoodPreview = { },
            onNavigateToSettings = { },
            onNavigateToAddMood = { },
            onChangeSelectedDate = { },
            onNavigateToStatistics = { }
        )
    }
}