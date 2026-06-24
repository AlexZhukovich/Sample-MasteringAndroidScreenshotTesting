package com.alexzh.moodtracker.statistics.components.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.core.domain.model.IconShape
import com.alexzh.designsystem.core.painter.toImageBitmap

@Composable
fun AverageDailyMoodChart(
    modifier: Modifier = Modifier,
    data: List<ChartDataItem>,
    iconShape: IconShape,
    iconProvider: @Composable (Float) -> Painter? = ChartDefaults.defaultHappinessIconProvider(iconShape),
    barColorProvider: (Float) -> Color = ChartDefaults.defaultBarColorProvider(),
    maxValue: Int = 5,
    scrollPosition: Int = 0,
    iconSize: Dp = 26.dp,
    barWidth: Dp = 20.dp,
    barSpacing: Dp = 20.dp,
    yAxisAreaWidth: Dp = 32.dp,
    yAxisSpacing: Dp = 10.dp,
    labelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    labelTextSize: TextUnit = 14.sp
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl

    val yAxisSteps = maxValue
    val iconPainters = (0..yAxisSteps).map { i ->
        iconProvider(i.toFloat())
    }
    val iconSizePx = with(density) { iconSize.toPx() }
    val axisBitmaps = iconPainters.map { painter ->
        painter?.toImageBitmap(
            size = Size(iconSizePx, iconSizePx),
            density = density,
            layoutDirection = layoutDirection
        )
    }

    val yAxisAreaWidthPx = with(density) { yAxisAreaWidth.toPx() }
    val yAxisSpacingPx = with(density) { yAxisSpacing.toPx() }
    val barWidthPx = with(density) { barWidth.toPx() }
    val barSpacingPx = with(density) { barSpacing.toPx() }
    val labelSizeDp = with(density) { labelTextSize.toPx().toDp() }

    val totalBars = data.size
    val totalChartWidthDp = (barWidth + barSpacing) * totalBars
    val scrollState = rememberScrollState()

    LaunchedEffect(data) {
        scrollState.scrollTo(
            with(density) {
                (barWidth + barSpacing).toPx() * (scrollPosition - 1)
            }.toInt()
        )
    }

    Row(
        modifier = modifier
            .padding(top = iconSize / 2, bottom = labelSizeDp + yAxisSpacing)
    ) {
        // Y-Axis icons
        Canvas(
            modifier = Modifier
                .width(yAxisAreaWidth)
                .fillMaxHeight()
        ) {
            val chartHeight = size.height
            val stepHeight = chartHeight / yAxisSteps

            axisBitmaps.forEachIndexed { i, iconBitmap ->
                val y = chartHeight - (i * stepHeight)
                if (iconBitmap != null) {
                    val iconX = if (isRtl) {
                        10f.toInt()
                    } else {
                        (yAxisAreaWidthPx - iconSizePx - 10f).toInt()
                    }
                    drawImage(
                        image = iconBitmap,
                        srcOffset = IntOffset.Zero,
                        srcSize = IntSize(iconBitmap.width, iconBitmap.height),
                        dstOffset = IntOffset(
                            x = iconX,
                            y = (y - iconSizePx / 2).toInt()
                        ),
                        dstSize = IntSize(iconSizePx.toInt(), iconSizePx.toInt()),
                    )
                }
            }
        }

        // Bars & Labels
        Box(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .width(totalChartWidthDp + yAxisSpacing)
                .fillMaxHeight()
        ) {
            Canvas(modifier = Modifier.fillMaxHeight()) {
                val chartHeight = size.height

                data.forEachIndexed { index, item ->
                    val barHeight = (item.value / maxValue) * chartHeight
                    val left = if (isRtl) {
                        size.width - yAxisSpacingPx - barWidthPx - index * (barWidthPx + barSpacingPx)
                    } else {
                        yAxisSpacingPx + index * (barWidthPx + barSpacingPx)
                    }
                    val top = chartHeight - barHeight

                    // Bar Background
                    drawRoundRect(
                        color = barColorProvider(item.value).copy(alpha = 0.2f),
                        topLeft = Offset(x = left, y = 0f),
                        size = Size(width = barWidthPx, height = chartHeight),
                        cornerRadius = CornerRadius(barWidthPx / 2, barWidthPx / 2)
                    )

                    // Bar
                    drawRoundRect(
                        color = barColorProvider(item.value),
                        topLeft = Offset(x = left, y = top),
                        size = Size(width = barWidthPx, height = barHeight),
                        cornerRadius = CornerRadius(barWidthPx / 2, barWidthPx / 2)
                    )

                    val label = item.label
                    val textLayoutResult = textMeasurer.measure(
                        text = label,
                        style = TextStyle(color = labelColor, fontSize = labelTextSize)
                    )

                    val labelX = left + barWidthPx / 2 - textLayoutResult.size.width / 2
                    val labelY = chartHeight + labelTextSize.toPx() / 1.5f

                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = Offset(x = labelX, y = labelY),
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_VerticalBarChart(
    @PreviewParameter(IconShapeProviderProvider::class) iconShape: IconShape
) {
    val data = listOf(
        ChartDataItem("1", 4.3f),
        ChartDataItem("2", 2f),
        ChartDataItem("3", 3.7f),
        ChartDataItem("4", 2f),
        ChartDataItem("5", 1.7f),
        ChartDataItem("6", 3.5f),
        ChartDataItem("7", 4.25f),
        ChartDataItem("8", 3.75f),
        ChartDataItem("9", 2.5f)
    )

    AppTheme {
        Surface {
            AverageDailyMoodChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                data = data,
                iconShape = iconShape
            )
        }
    }
}

class IconShapeProviderProvider : PreviewParameterProvider<IconShape> {
    override val values: Sequence<IconShape>
        get() = sequenceOf(
            IconShape.CIRCLE,
            IconShape.ROUNDED_SQUARE
        )
}