package com.alexzh.moodtracker.core.domain.model

data class SegmentedActionImpact(
    val positiveImpact: List<ActionToHappiness>,
    val negativeImpact: List<ActionToHappiness>
)