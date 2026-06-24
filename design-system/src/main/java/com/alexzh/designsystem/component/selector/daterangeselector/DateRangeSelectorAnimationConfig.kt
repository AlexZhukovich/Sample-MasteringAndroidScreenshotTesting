package com.alexzh.designsystem.component.selector.daterangeselector

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith

data class DateRangeSelectorAnimationConfig(
    val durationMs: Int = 300,
    val delayMs: Long = 350L
) {
    fun getTransitionSpec(direction: PeriodChangeDirection, isRtl: Boolean = false): ContentTransform {
        val rtlMultiplier = if (isRtl) -1 else 1
        return when (direction) {
            PeriodChangeDirection.NEXT -> {
                slideInHorizontally(
                    animationSpec = tween(durationMs),
                    initialOffsetX = { fullWidth -> fullWidth * rtlMultiplier }
                ) togetherWith slideOutHorizontally(
                    animationSpec = tween(durationMs),
                    targetOffsetX = { fullWidth -> -fullWidth * rtlMultiplier }
                )
            }
            PeriodChangeDirection.PREVIOUS -> {
                slideInHorizontally(
                    animationSpec = tween(durationMs),
                    initialOffsetX = { fullWidth -> -fullWidth * rtlMultiplier }
                ) togetherWith slideOutHorizontally(
                    animationSpec = tween(durationMs),
                    targetOffsetX = { fullWidth -> fullWidth * rtlMultiplier }
                )
            }
            PeriodChangeDirection.NONE -> {
                slideInHorizontally(
                    animationSpec = tween(0),
                    initialOffsetX = { 0 }
                ) togetherWith slideOutHorizontally(
                    animationSpec = tween(0),
                    targetOffsetX = { 0 }
                )
            }
        }
    }
}