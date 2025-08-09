package com.alexzh.moodtracker.ui.designsystem.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

@Composable
fun ActionToHappinessChart(
    modifier: Modifier = Modifier,
    data: List<ChartDataItem>,
    barColorProvider: (Float) -> Color = ChartDefaults.defaultBarColorProvider(),
    labelColor: Color = MaterialTheme.colorScheme.onSurface,
    cornerRadius: Dp = 10.dp,
    barHeight: Dp = 40.dp,
    spacingBetweenItems: Dp = 8.dp,
    textStartPadding: Dp = 16.dp,
    maxValue: Float = 5.0f
) {
    val itemCount = data.size
    val totalBarsHeight = itemCount * barHeight
    val totalSpacingHeight = (itemCount - 1) * spacingBetweenItems
    val totalHeight = totalBarsHeight + totalSpacingHeight
    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(totalHeight)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cornerRadiusPx = cornerRadius.toPx()
            val barHeightPx = barHeight.toPx()
            val spacingPx = spacingBetweenItems.toPx()
            val startPaddingPx = textStartPadding.toPx()
            val cornerRadiusResult = CornerRadius(cornerRadiusPx, cornerRadiusPx)

            val textStyle = TextStyle(
                color = labelColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )

            data.forEachIndexed { index, item ->
                val label = item.label
                val barColor = barColorProvider(item.value)
                val barWidth = (item.value / maxValue) * (size.width)
                val topY = index * (barHeightPx + spacingPx)

                // Bar background
                drawRoundRect(
                    color = barColor.copy(alpha = 0.2f),
                    topLeft = Offset(x = 0f, y = topY),
                    size = Size(size.width, barHeightPx),
                    cornerRadius = cornerRadiusResult
                )

                // Bar progress
                drawRoundRect(
                    color = barColor,
                    topLeft = Offset(x = 0f, y = topY),
                    size = Size(barWidth, barHeightPx),
                    cornerRadius = cornerRadiusResult
                )

                val textLayoutResult = textMeasurer.measure(text = label, style = textStyle)
                val textHeight = textLayoutResult.size.height
                val y = topY + (barHeightPx - textHeight) / 2f
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(startPaddingPx, y)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_ActivityToHappinessChart() {
    val data = listOf(
        ChartDataItem("Reading", 5.0f),
        ChartDataItem("Walking", 4.5f),
        ChartDataItem("Shopping", 2.7f),
        ChartDataItem("Cooking", 1.2f),
    )

    ActionToHappinessChart(
        data = data
    )
}