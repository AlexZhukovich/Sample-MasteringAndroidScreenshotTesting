package com.alexzh.moodtracker.ui.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.ui.designsystem.chip.Chip
import com.alexzh.moodtracker.ui.designsystem.chip.ChipSize
import com.alexzh.moodtracker.ui.model.ActionItem
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        actions.forEach { action ->
            Chip(
                text = action.name,
                size = ChipSize.Small
            )
        }
    }
}