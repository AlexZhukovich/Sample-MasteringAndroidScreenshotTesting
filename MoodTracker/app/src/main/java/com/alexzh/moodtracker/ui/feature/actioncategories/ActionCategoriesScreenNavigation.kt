package com.alexzh.moodtracker.ui.feature.actioncategories

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object ActionCategoriesScreenDestination

fun NavGraphBuilder.actionCategoriesScreen(
    onNavigateToEditActionCategory: (actionCategoryId: Long) -> Unit,
    onNavigateUp: () -> Unit
) {
    composable<ActionCategoriesScreenDestination> {
        ActionCategoriesScreen(
            viewModel = koinViewModel<ActionCategoriesScreenViewModel>(),
            onNavigateToEditActionCategory = onNavigateToEditActionCategory,
            onNavigateUp = onNavigateUp
        )
    }
}