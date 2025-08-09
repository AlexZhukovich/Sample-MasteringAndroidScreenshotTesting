package com.alexzh.moodtracker.ui.designsystem.chart

import androidx.compose.ui.graphics.Color
import com.alexzh.moodtracker.ui.theme.chartColorHighValuesDark
import com.alexzh.moodtracker.ui.theme.chartColorHighValuesLight
import com.alexzh.moodtracker.ui.theme.chartColorLowValuesDark
import com.alexzh.moodtracker.ui.theme.chartColorLowValuesLight
import com.alexzh.moodtracker.ui.theme.chartColorMediumValuesDark
import com.alexzh.moodtracker.ui.theme.chartColorMediumValuesLight
import com.alexzh.moodtracker.ui.theme.chartColorZeroDark
import com.alexzh.moodtracker.ui.theme.chartColorZeroLight

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