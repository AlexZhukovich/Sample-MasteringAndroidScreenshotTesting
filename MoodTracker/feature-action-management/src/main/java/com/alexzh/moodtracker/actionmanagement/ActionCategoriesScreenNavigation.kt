package com.alexzh.moodtracker.actionmanagement

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object ActionCategoriesScreenDestination

fun NavGraphBuilder.actionCategoriesScreen(
    onNavigateUp: () -> Unit
) {
    composable<ActionCategoriesScreenDestination> {
        ActionCategoriesScreen(
            viewModel = koinViewModel<ActionCategoriesScreenViewModel>(),
            onNavigateUp = onNavigateUp
        )
    }
}