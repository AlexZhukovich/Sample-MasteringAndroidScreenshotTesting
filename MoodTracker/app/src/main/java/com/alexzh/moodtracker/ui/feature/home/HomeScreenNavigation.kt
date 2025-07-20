package com.alexzh.moodtracker.ui.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object HomeScreenDestination

fun NavGraphBuilder.homeScreen(
    onNavigateToMoodPreview: (moodId: Long) -> Unit,
    onNavigateToAddMood: () -> Unit
) {
    composable<HomeScreenDestination> {
        HomeScreen(
            viewModel = koinViewModel<HomeScreenViewModel>(),
            onNavigateToMoodPreview = onNavigateToMoodPreview,
            onNavigateToAddMood = onNavigateToAddMood
        )
    }
}