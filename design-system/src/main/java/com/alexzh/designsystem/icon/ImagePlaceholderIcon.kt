package com.alexzh.designsystem.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private var imagePlaceholderIcon: ImageVector? = null

val ImagePlaceholderIcon: ImageVector
    get() {
        if (imagePlaceholderIcon != null) {
            return imagePlaceholderIcon!!
        }
        imagePlaceholderIcon = Builder(name = "ImagePlaceholder", defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(212.31f, 820.0f)
                quadTo(182.0f, 820.0f, 161.0f, 799.0f)
                quadToRelative(-21.0f, -21.0f, -21.0f, -51.31f)
                verticalLineToRelative(-535.38f)
                quadTo(140.0f, 182.0f, 161.0f, 161.0f)
                quadToRelative(21.0f, -21.0f, 51.31f, -21.0f)
                horizontalLineToRelative(535.38f)
                quadTo(778.0f, 140.0f, 799.0f, 161.0f)
                quadToRelative(21.0f, 21.0f, 21.0f, 51.31f)
                verticalLineToRelative(535.38f)
                quadTo(820.0f, 778.0f, 799.0f, 799.0f)
                quadToRelative(-21.0f, 21.0f, -51.31f, 21.0f)
                lineTo(212.31f, 820.0f)
                close()
                moveTo(212.31f, 760.0f)
                horizontalLineToRelative(535.38f)
                quadToRelative(4.62f, 0.0f, 8.46f, -3.85f)
                quadToRelative(3.85f, -3.84f, 3.85f, -8.46f)
                verticalLineToRelative(-535.38f)
                quadToRelative(0.0f, -4.62f, -3.85f, -8.46f)
                quadToRelative(-3.84f, -3.85f, -8.46f, -3.85f)
                lineTo(212.31f, 200.0f)
                quadToRelative(-4.62f, 0.0f, -8.46f, 3.85f)
                quadToRelative(-3.85f, 3.84f, -3.85f, 8.46f)
                verticalLineToRelative(535.38f)
                quadToRelative(0.0f, 4.62f, 3.85f, 8.46f)
                quadToRelative(3.84f, 3.85f, 8.46f, 3.85f)
                close()
                moveTo(200.0f, 760.0f)
                verticalLineToRelative(-560.0f)
                verticalLineToRelative(560.0f)
                close()
                moveTo(306.16f, 670.0f)
                horizontalLineToRelative(350.76f)
                quadToRelative(10.85f, 0.0f, 16.08f, -9.85f)
                quadToRelative(5.23f, -9.84f, -1.62f, -19.07f)
                lineTo(576.0f, 513.31f)
                quadToRelative(-5.62f, -7.23f, -14.46f, -7.23f)
                quadToRelative(-8.85f, 0.0f, -14.46f, 7.23f)
                lineToRelative(-97.85f, 127.46f)
                lineToRelative(-65.54f, -84.0f)
                quadToRelative(-5.61f, -6.85f, -14.27f, -6.85f)
                quadToRelative(-8.65f, 0.0f, -14.27f, 7.24f)
                lineToRelative(-63.07f, 83.92f)
                quadToRelative(-7.23f, 9.23f, -2.0f, 19.07f)
                quadToRelative(5.23f, 9.85f, 16.08f, 9.85f)
                close()
            }
        }
        .build()
        return imagePlaceholderIcon!!
    }

@Preview
@Composable
private fun Preview_ImagePlaceholderIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = ImagePlaceholderIcon, contentDescription = "")
    }
}
