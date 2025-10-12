package com.alexzh.moodtracker.domain.model

data class SegmentedActionImpact(
    val positiveImpact: List<ActionToHappiness>,
    val negativeImpact: List<ActionToHappiness>
)