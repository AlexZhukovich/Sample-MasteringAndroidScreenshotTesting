package com.alexzh.moodtracker.home.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.component.chip.Chip
import com.alexzh.designsystem.component.chip.ChipSize
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.ui.model.ActionItem
import kotlin.collections.forEach

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoodActionChips(
    modifier: Modifier = Modifier,
    actions: List<ActionItem>
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        actions.forEach { action ->
            Chip(
                text = action.name,
                size = ChipSize.Small
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview_MoodActionChips_MultipleActions() {
    AppTheme {
        MoodActionChips(
            actions = listOf(
                ActionItem(id = 1L, name = "Running"),
                ActionItem(id = 2L, name = "Cycling"),
                ActionItem(id = 3L, name = "Swimming"),
                ActionItem(id = 4L, name = "Yoga"),
                ActionItem(id = 5L, name = "Meditation")
            )
        )
    }
}