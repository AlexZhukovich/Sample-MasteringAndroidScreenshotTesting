package com.alexzh.moodtracker.ui.feature.statistics.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.ui.designsystem.chart.ChartColors
import com.alexzh.moodtracker.ui.theme.AppTheme

@Composable
fun StatisticsEmptyStateAnimatedIcon(
    modifier: Modifier = Modifier,
    chartLineColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.4f),
    borderColor: Color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.78f),
    gridLineColor: Color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4f),
    isDark: Boolean = isSystemInDarkTheme()
) {
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000, easing = EaseInOutCubic)
        )
    }

    val lineProgress = animationProgress.value

    Canvas(
        modifier = modifier
            .size(width = 160.dp, height = 120.dp)
            .fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val chartOffset = Offset(0f, 0f)
        val chartSize = Size(canvasWidth, canvasHeight)
        val baseScale = minOf(canvasWidth, canvasHeight) / 120f
        val cornerRadius = (8 * baseScale)
        val strokeWidth = (2 * baseScale)

        drawRoundRect(
            color = borderColor,
            topLeft = chartOffset,
            size = chartSize,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius),
            style = Stroke(width = strokeWidth)
        )

        drawRoundRect(
            color = backgroundColor,
            topLeft = chartOffset,
            size = chartSize,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius)
        )

        // Grid lines
        for (i in 1..4) {
            val y = chartOffset.y + i * chartSize.height / 5
            drawLine(
                color = gridLineColor,
                start = Offset(chartOffset.x, y),
                end = Offset(chartOffset.x + chartSize.width, y),
                strokeWidth = 1.dp.toPx()
            )
        }

        for (i in 1..4) {
            val x = chartOffset.x + i * chartSize.width / 5
            drawLine(
                color = gridLineColor,
                start = Offset(x, chartOffset.y),
                end = Offset(x, chartOffset.y + chartSize.height),
                strokeWidth = 1.dp.toPx()
            )
        }

        val dataPoints = listOf(
            Offset(chartOffset.x + chartSize.width * 0.1f, chartOffset.y + chartSize.height * 0.75f),
            Offset(chartOffset.x + chartSize.width * 0.3f, chartOffset.y + chartSize.height * 0.60f),
            Offset(chartOffset.x + chartSize.width * 0.5f, chartOffset.y + chartSize.height * 0.50f),
            Offset(chartOffset.x + chartSize.width * 0.7f, chartOffset.y + chartSize.height * 0.35f),
            Offset(chartOffset.x + chartSize.width * 0.9f, chartOffset.y + chartSize.height * 0.25f)
        )

        val colors = listOf(
            ChartColors.LOW_VALUES.getColor(isDark),
            ChartColors.MEDIUM_VALUES.getColor(isDark),
            ChartColors.MEDIUM_VALUES.getColor(isDark),
            ChartColors.HIGH_VALUES.getColor(isDark),
            ChartColors.HIGH_VALUES.getColor(isDark)
        )

        // Animated trend line
        if (lineProgress > 0f) {
            val path = Path()
            val totalPoints = dataPoints.size
            val currentSegment = (lineProgress * (totalPoints - 1)).coerceAtMost((totalPoints - 1).toFloat())
            val segmentIndex = currentSegment.toInt()
            val segmentProgress = currentSegment - segmentIndex

            if (segmentIndex < totalPoints) {
                path.moveTo(dataPoints[0].x, dataPoints[0].y)

                // Draw completed segments
                for (i in 1..segmentIndex) {
                    path.lineTo(dataPoints[i].x, dataPoints[i].y)
                }

                // Draw partial segment if we're not at the end
                if (segmentIndex < totalPoints - 1 && segmentProgress > 0f) {
                    val startPoint = dataPoints[segmentIndex]
                    val endPoint = dataPoints[segmentIndex + 1]
                    val animatedPoint = Offset(
                        startPoint.x + (endPoint.x - startPoint.x) * segmentProgress,
                        startPoint.y + (endPoint.y - startPoint.y) * segmentProgress
                    )
                    path.lineTo(animatedPoint.x, animatedPoint.y)
                }

                drawPath(
                    path = path,
                    color = chartLineColor,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        // Data points
        dataPoints.forEachIndexed { index, point ->
            if (lineProgress * dataPoints.size > index) {
                drawCircle(
                    color = colors[index],
                    radius = 3.dp.toPx(),
                    center = point
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Chart Animation")
@Composable
fun Preview_StatisticsEmptyStateAnimatedIcon() {
    AppTheme {
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            StatisticsEmptyStateAnimatedIcon()
        }
    }
}