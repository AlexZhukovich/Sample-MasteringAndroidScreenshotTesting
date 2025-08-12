package com.alexzh.moodtracker.ui.feature.editmood

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class EditMoodScreenDestination(
    val moodId: Long = 0L
)

fun NavGraphBuilder.editMoodScreen(
    onNavigateToActionCategories: () -> Unit,
    onNavigateUp: () -> Unit
) {
    composable<EditMoodScreenDestination> {
        EditMoodScreen(
            viewModel = koinViewModel<EditMoodScreenViewModel>(),
            onNavigateToActionCategories= onNavigateToActionCategories,
            onNavigateUp = onNavigateUp
        )
    }
}