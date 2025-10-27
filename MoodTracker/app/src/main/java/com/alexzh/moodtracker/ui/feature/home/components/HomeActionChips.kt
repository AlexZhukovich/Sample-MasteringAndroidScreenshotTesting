package com.alexzh.moodtracker.ui.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.ui.designsystem.chip.Chip
import com.alexzh.moodtracker.ui.designsystem.chip.ChipSize
import com.alexzh.moodtracker.ui.model.ActionItem
import com.alexzh.moodtracker.ui.theme.AppTheme
import kotlin.collections.forEach

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoodActionChips(
    actions: List<ActionItem>,
    modifier: Modifier = Modifier
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