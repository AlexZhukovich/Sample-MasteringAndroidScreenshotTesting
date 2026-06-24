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

private var arrowBackIcon: ImageVector? = null

val ArrowBackIcon: ImageVector
    get() {
        if (arrowBackIcon != null) {
            return arrowBackIcon!!
        }
        arrowBackIcon = Builder(name = "ArrowBack", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f, autoMirror = true).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveToRelative(294.92f, 510.0f)
                lineToRelative(206.77f, 206.77f)
                quadToRelative(8.92f, 8.92f, 8.81f, 20.88f)
                quadToRelative(-0.12f, 11.96f, -9.42f, 21.27f)
                quadToRelative(-9.31f, 8.69f, -21.08f, 9.0f)
                quadToRelative(-11.77f, 0.31f, -21.08f, -9.0f)
                lineTo(205.31f, 505.31f)
                quadToRelative(-5.62f, -5.62f, -7.92f, -11.85f)
                quadToRelative(-2.31f, -6.23f, -2.31f, -13.46f)
                reflectiveQuadToRelative(2.31f, -13.46f)
                quadToRelative(2.3f, -6.23f, 7.92f, -11.85f)
                lineToRelative(253.61f, -253.61f)
                quadToRelative(8.31f, -8.31f, 20.58f, -8.5f)
                quadToRelative(12.27f, -0.19f, 21.58f, 8.5f)
                quadToRelative(9.3f, 9.31f, 9.3f, 21.38f)
                quadToRelative(0.0f, 12.08f, -9.3f, 21.39f)
                lineTo(294.92f, 450.0f)
                lineTo(750.0f, 450.0f)
                quadToRelative(12.77f, 0.0f, 21.38f, 8.62f)
                quadTo(780.0f, 467.23f, 780.0f, 480.0f)
                reflectiveQuadToRelative(-8.62f, 21.38f)
                quadTo(762.77f, 510.0f, 750.0f, 510.0f)
                lineTo(294.92f, 510.0f)
                close()
            }
        }
        .build()
        return arrowBackIcon!!
    }

@Preview
@Composable
private fun Preview_ArrowBackIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = ArrowBackIcon, contentDescription = "")
    }
}
