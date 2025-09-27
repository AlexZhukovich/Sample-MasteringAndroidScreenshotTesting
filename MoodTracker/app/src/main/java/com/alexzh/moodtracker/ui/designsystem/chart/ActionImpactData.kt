package com.alexzh.moodtracker.ui.designsystem.chart

data class ActionImpactData(
    val positiveImpactData: List<ChartDataItem>,
    val negativeImpactData: List<ChartDataItem>
) {
    fun isNotEmpty(): Boolean = positiveImpactData.isNotEmpty() || negativeImpactData.isNotEmpty()

    fun isEmpty(): Boolean = !isNotEmpty()
}