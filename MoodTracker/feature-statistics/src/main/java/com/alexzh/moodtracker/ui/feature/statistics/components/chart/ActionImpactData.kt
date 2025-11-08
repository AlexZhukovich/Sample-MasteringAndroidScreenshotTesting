package com.alexzh.moodtracker.ui.feature.statistics.components.chart

data class ActionImpactData(
    val positiveImpactData: List<ChartDataItem>,
    val negativeImpactData: List<ChartDataItem>
) {
    fun isNotEmpty(): Boolean = positiveImpactData.isNotEmpty() || negativeImpactData.isNotEmpty()

    fun isEmpty(): Boolean = !isNotEmpty()
}