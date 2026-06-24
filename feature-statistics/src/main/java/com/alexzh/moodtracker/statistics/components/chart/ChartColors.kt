package com.alexzh.moodtracker.statistics.components.chart

import androidx.compose.ui.graphics.Color
import com.alexzh.designsystem.core.theme.chartColorHighValuesDark
import com.alexzh.designsystem.core.theme.chartColorHighValuesLight
import com.alexzh.designsystem.core.theme.chartColorLowValuesDark
import com.alexzh.designsystem.core.theme.chartColorLowValuesLight
import com.alexzh.designsystem.core.theme.chartColorMediumValuesDark
import com.alexzh.designsystem.core.theme.chartColorMediumValuesLight
import com.alexzh.designsystem.core.theme.chartColorZeroDark
import com.alexzh.designsystem.core.theme.chartColorZeroLight

enum class ChartColors(
    private val lightColor: Color,
    private val darkColor: Color
) {
    ZERO(chartColorZeroLight, chartColorZeroDark),
    LOW_VALUES(chartColorLowValuesLight, chartColorLowValuesDark),
    MEDIUM_VALUES(chartColorMediumValuesLight, chartColorMediumValuesDark),
    HIGH_VALUES(chartColorHighValuesLight, chartColorHighValuesDark);

    fun getColor(isDark: Boolean): Color = if (isDark) darkColor else lightColor
}