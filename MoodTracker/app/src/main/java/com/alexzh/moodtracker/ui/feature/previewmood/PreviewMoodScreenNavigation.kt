package com.alexzh.moodtracker.ui.feature.previewmood

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class PreviewMoodScreenDestination(
    val moodId: Long = 0L
)

fun NavGraphBuilder.previewMoodScreen(
    onNavigateToEditMood: (moodId: Long) -> Unit,
    onNavigateUp: () -> Unit
) {
    composable<PreviewMoodScreenDestination> {
        PreviewMoodScreen(
            viewModel = koinViewModel<PreviewMoodScreenViewModel>(),
            onNavigateToEditMood = onNavigateToEditMood,
            onNavigateUp = onNavigateUp
        )
    }
}