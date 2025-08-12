package com.alexzh.moodtracker.ui.feature.actioncategorydetails

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class ActionCategoryDetailsDestination(
    val actionCategoryId: Long
)

fun NavGraphBuilder.actionCategoryDetailsScreen(
    onNavigateUp: () -> Unit
) {
    composable<ActionCategoryDetailsDestination> {
        ActionCategoryDetailsScreen(
            viewModel = koinViewModel<ActionCategoryDetailsScreenViewModel>(),
            onNavigateUp = onNavigateUp
        )
    }
}