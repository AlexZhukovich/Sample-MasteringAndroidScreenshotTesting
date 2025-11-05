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

private var homeIcon: ImageVector? = null

val HomeIcon: ImageVector
    get() {
        if (homeIcon != null) {
            return homeIcon!!
        }
        homeIcon = Builder(name = "Home", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(240.0f, 760.0f)
                horizontalLineToRelative(133.85f)
                verticalLineToRelative(-201.54f)
                quadToRelative(0.0f, -15.36f, 10.39f, -25.76f)
                quadToRelative(10.4f, -10.39f, 25.76f, -10.39f)
                horizontalLineToRelative(140.0f)
                quadToRelative(15.36f, 0.0f, 25.76f, 10.39f)
                quadToRelative(10.39f, 10.4f, 10.39f, 25.76f)
                lineTo(586.15f, 760.0f)
                lineTo(720.0f, 760.0f)
                verticalLineToRelative(-353.85f)
                quadToRelative(0.0f, -3.07f, -1.35f, -5.57f)
                quadToRelative(-1.34f, -2.5f, -3.65f, -4.43f)
                lineTo(487.31f, 225.0f)
                quadToRelative(-3.08f, -2.69f, -7.31f, -2.69f)
                quadToRelative(-4.23f, 0.0f, -7.31f, 2.69f)
                lineTo(245.0f, 396.15f)
                quadToRelative(-2.31f, 1.93f, -3.65f, 4.43f)
                quadToRelative(-1.35f, 2.5f, -1.35f, 5.57f)
                lineTo(240.0f, 760.0f)
                close()
                moveTo(180.0f, 760.0f)
                verticalLineToRelative(-353.85f)
                quadToRelative(0.0f, -17.17f, 7.68f, -32.53f)
                quadToRelative(7.69f, -15.37f, 21.24f, -25.31f)
                lineToRelative(227.7f, -171.54f)
                quadToRelative(18.95f, -14.46f, 43.32f, -14.46f)
                reflectiveQuadToRelative(43.44f, 14.46f)
                lineToRelative(227.7f, 171.54f)
                quadToRelative(13.55f, 9.94f, 21.24f, 25.31f)
                quadToRelative(7.68f, 15.36f, 7.68f, 32.53f)
                lineTo(780.0f, 760.0f)
                quadToRelative(0.0f, 24.54f, -17.73f, 42.27f)
                quadTo(744.54f, 820.0f, 720.0f, 820.0f)
                lineTo(562.31f, 820.0f)
                quadToRelative(-15.37f, 0.0f, -25.76f, -10.4f)
                quadToRelative(-10.4f, -10.39f, -10.4f, -25.76f)
                verticalLineToRelative(-201.53f)
                horizontalLineToRelative(-92.3f)
                verticalLineToRelative(201.53f)
                quadToRelative(0.0f, 15.37f, -10.4f, 25.76f)
                quadToRelative(-10.39f, 10.4f, -25.76f, 10.4f)
                lineTo(240.0f, 820.0f)
                quadToRelative(-24.54f, 0.0f, -42.27f, -17.73f)
                quadTo(180.0f, 784.54f, 180.0f, 760.0f)
                close()
                moveTo(480.0f, 490.77f)
                close()
            }
        }
        .build()
        return homeIcon!!
    }



@Preview
@Composable
private fun Preview_HomeIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = HomeIcon, contentDescription = "")
    }
}
