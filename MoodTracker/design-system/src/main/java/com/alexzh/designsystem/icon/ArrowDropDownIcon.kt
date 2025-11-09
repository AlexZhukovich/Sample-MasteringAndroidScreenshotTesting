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

private var arrowDropDownIcon: ImageVector? = null

val ArrowDropDownIcon: ImageVector
    get() {
        if (arrowDropDownIcon != null) {
            return arrowDropDownIcon!!
        }
        arrowDropDownIcon = Builder(name = "ArrowDropDown", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(460.81f, 566.96f)
                lineTo(334.76f, 440.92f)
                quadToRelative(-2.6f, -2.61f, -4.1f, -5.83f)
                quadToRelative(-1.5f, -3.21f, -1.5f, -6.89f)
                quadToRelative(0.0f, -7.35f, 4.97f, -12.78f)
                quadToRelative(4.97f, -5.42f, 13.1f, -5.42f)
                horizontalLineToRelative(265.54f)
                quadToRelative(8.13f, 0.0f, 13.1f, 5.48f)
                quadToRelative(4.97f, 5.47f, 4.97f, 12.77f)
                quadToRelative(0.0f, 1.83f, -5.61f, 12.67f)
                lineTo(499.19f, 566.96f)
                quadToRelative(-4.34f, 4.35f, -8.98f, 6.35f)
                quadToRelative(-4.64f, 2.0f, -10.21f, 2.0f)
                quadToRelative(-5.57f, 0.0f, -10.21f, -2.0f)
                quadToRelative(-4.64f, -2.0f, -8.98f, -6.35f)
                close()
            }
        }
        .build()
        return arrowDropDownIcon!!
    }

@Preview
@Composable
private fun Preview_ArrowDropDownIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = ArrowDropDownIcon, contentDescription = "")
    }
}
