package com.alexzh.moodtracker.ui.feature.editmood

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import com.alexzh.moodtracker.ui.model.LocalizedMood

@Serializable
data class EditMoodScreenDestination(
    val moodId: Long = 0L,
    val preselectedMood: LocalizedMood? = null
)

fun NavGraphBuilder.editMoodScreen(
    onNavigateToActionCategories: () -> Unit,
    onNavigateUp: () -> Unit
) {
    composable<EditMoodScreenDestination>(
        deepLinks = listOf(
            navDeepLink<EditMoodScreenDestination>(
                basePath = "moodtracker://editMood"
            )
        )
    ) {
        EditMoodScreen(
            viewModel = koinViewModel<EditMoodScreenViewModel>(),
            onNavigateToActionCategories= onNavigateToActionCategories,
            onNavigateUp = onNavigateUp
        )
    }
}