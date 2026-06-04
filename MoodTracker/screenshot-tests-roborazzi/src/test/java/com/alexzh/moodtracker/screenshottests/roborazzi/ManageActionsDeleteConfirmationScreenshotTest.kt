package com.alexzh.moodtracker.screenshottests.roborazzi

import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.performClick
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenContent
import com.alexzh.moodtracker.actionmanagement.ActionCategoriesScreenUiState
import com.alexzh.moodtracker.common.ui.model.ActionCategoryItem
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Screenshot tests for the delete confirmation dialog triggered via UI interaction.
 *
 * Configuration notes:
 * - @Config(sdk = [33]) specifies API level; hardware rendering requires API 29+ for shadow support
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel4, sdk = [33])
class ManageActionsDeleteConfirmationScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun manageAction_deleteConfirmationDialog() {
        val uiState = ActionCategoriesScreenUiState(
            categories = listOf(
                ActionCategoryItem(id = 1L, name = "Physical Activity"),
                ActionCategoryItem(id = 2L, name = "Social Activity"),
                ActionCategoryItem(id = 3L, name = "Relaxation"),
                ActionCategoryItem(id = 4L, name = "Productivity")
            )
        )

        composeTestRule.setContent {
            AppTheme {
                ActionCategoriesScreenContent(
                    uiState = uiState,
                    onAddCategory = { _ -> },
                    onEditCategory = { _, _ -> },
                    onDeleteCategory = { _ -> },
                    onSelectCategory = { _ -> },
                    onClearCategorySelection = { },
                    onAddAction = { _ -> },
                    onEditAction = { _, _ -> },
                    onDeleteAction = { _ -> },
                    onNavigateUp = { }
                )
            }
        }

        composeTestRule.onNode(
            hasContentDescription("Delete") and hasAnyAncestor(hasText("Relaxation"))
        ).performClick()

        composeTestRule.onAllNodes(isRoot()).onLast().captureRoboImage()
    }
}