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

private var keyboardArrowLeftIcon: ImageVector? = null

val KeyboardArrowLeftIcon: ImageVector
    get() {
        if (keyboardArrowLeftIcon != null) {
            return keyboardArrowLeftIcon!!
        }
        keyboardArrowLeftIcon = Builder(name = "KeyboardArrowLeft", defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f, autoMirror = true).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveToRelative(418.15f, 480.0f)
                lineToRelative(162.93f, 162.92f)
                quadToRelative(8.3f, 8.31f, 8.5f, 20.89f)
                quadToRelative(0.19f, 12.57f, -8.5f, 21.27f)
                quadToRelative(-8.7f, 8.69f, -21.08f, 8.69f)
                quadToRelative(-12.38f, 0.0f, -21.08f, -8.69f)
                lineTo(359.15f, 505.31f)
                quadToRelative(-5.61f, -5.62f, -7.92f, -11.85f)
                quadToRelative(-2.31f, -6.23f, -2.31f, -13.46f)
                reflectiveQuadToRelative(2.31f, -13.46f)
                quadToRelative(2.31f, -6.23f, 7.92f, -11.85f)
                lineToRelative(179.77f, -179.77f)
                quadToRelative(8.31f, -8.3f, 20.89f, -8.5f)
                quadToRelative(12.57f, -0.19f, 21.27f, 8.5f)
                quadToRelative(8.69f, 8.7f, 8.69f, 21.08f)
                quadToRelative(0.0f, 12.38f, -8.69f, 21.08f)
                lineTo(418.15f, 480.0f)
                close()
            }
        }
        .build()
        return keyboardArrowLeftIcon!!
    }

@Preview
@Composable
private fun Preview_KeyboardArrowLeftIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = KeyboardArrowLeftIcon, contentDescription = "")
    }
}
