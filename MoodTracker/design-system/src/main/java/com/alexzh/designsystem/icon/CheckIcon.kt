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

private var checkIcon: ImageVector? = null

val CheckIcon: ImageVector
    get() {
        if (checkIcon != null) {
            return checkIcon!!
        }
        checkIcon = Builder(name = "Check", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveToRelative(382.0f, 620.62f)
                lineToRelative(345.54f, -345.54f)
                quadToRelative(8.92f, -8.93f, 20.88f, -9.12f)
                quadToRelative(11.96f, -0.19f, 21.27f, 9.12f)
                quadToRelative(9.31f, 9.31f, 9.31f, 21.38f)
                quadToRelative(0.0f, 12.08f, -9.31f, 21.39f)
                lineToRelative(-362.38f, 363.0f)
                quadToRelative(-10.85f, 10.84f, -25.31f, 10.84f)
                quadToRelative(-14.46f, 0.0f, -25.31f, -10.84f)
                lineToRelative(-167.0f, -167.0f)
                quadToRelative(-8.92f, -8.93f, -8.8f, -21.2f)
                quadToRelative(0.11f, -12.26f, 9.42f, -21.57f)
                reflectiveQuadToRelative(21.38f, -9.31f)
                quadToRelative(12.08f, 0.0f, 21.39f, 9.31f)
                lineTo(382.0f, 620.62f)
                close()
            }
        }
        .build()
        return checkIcon!!
    }

@Preview
@Composable
private fun Preview_CheckIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = CheckIcon, contentDescription = "")
    }
}
