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

private var deleteIcon: ImageVector? = null

val DeleteIcon: ImageVector
    get() {
        if (deleteIcon != null) {
            return deleteIcon!!
        }
        deleteIcon = Builder(name = "Delete", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(292.31f, 820.0f)
                quadToRelative(-29.83f, 0.0f, -51.07f, -21.24f)
                quadTo(220.0f, 777.52f, 220.0f, 747.69f)
                lineTo(220.0f, 240.0f)
                horizontalLineToRelative(-10.0f)
                quadToRelative(-12.75f, 0.0f, -21.37f, -8.63f)
                quadToRelative(-8.63f, -8.63f, -8.63f, -21.38f)
                quadToRelative(0.0f, -12.76f, 8.63f, -21.37f)
                quadTo(197.25f, 180.0f, 210.0f, 180.0f)
                horizontalLineToRelative(150.0f)
                quadToRelative(0.0f, -14.69f, 10.35f, -25.04f)
                quadToRelative(10.34f, -10.34f, 25.03f, -10.34f)
                horizontalLineToRelative(169.24f)
                quadToRelative(14.69f, 0.0f, 25.03f, 10.34f)
                quadTo(600.0f, 165.31f, 600.0f, 180.0f)
                horizontalLineToRelative(150.0f)
                quadToRelative(12.75f, 0.0f, 21.37f, 8.63f)
                quadToRelative(8.63f, 8.63f, 8.63f, 21.38f)
                quadToRelative(0.0f, 12.76f, -8.63f, 21.37f)
                quadTo(762.75f, 240.0f, 750.0f, 240.0f)
                horizontalLineToRelative(-10.0f)
                verticalLineToRelative(507.69f)
                quadToRelative(0.0f, 29.83f, -21.24f, 51.07f)
                quadTo(697.52f, 820.0f, 667.69f, 820.0f)
                lineTo(292.31f, 820.0f)
                close()
                moveTo(680.0f, 240.0f)
                lineTo(280.0f, 240.0f)
                verticalLineToRelative(507.69f)
                quadToRelative(0.0f, 5.39f, 3.46f, 8.85f)
                reflectiveQuadToRelative(8.85f, 3.46f)
                horizontalLineToRelative(375.38f)
                quadToRelative(5.39f, 0.0f, 8.85f, -3.46f)
                reflectiveQuadToRelative(3.46f, -8.85f)
                lineTo(680.0f, 240.0f)
                close()
                moveTo(406.17f, 680.0f)
                quadToRelative(12.75f, 0.0f, 21.37f, -8.62f)
                quadToRelative(8.61f, -8.63f, 8.61f, -21.38f)
                verticalLineToRelative(-300.0f)
                quadToRelative(0.0f, -12.75f, -8.63f, -21.38f)
                quadToRelative(-8.62f, -8.62f, -21.38f, -8.62f)
                quadToRelative(-12.75f, 0.0f, -21.37f, 8.62f)
                quadToRelative(-8.61f, 8.63f, -8.61f, 21.38f)
                verticalLineToRelative(300.0f)
                quadToRelative(0.0f, 12.75f, 8.62f, 21.38f)
                quadToRelative(8.63f, 8.62f, 21.39f, 8.62f)
                close()
                moveTo(553.86f, 680.0f)
                quadToRelative(12.75f, 0.0f, 21.37f, -8.62f)
                quadToRelative(8.61f, -8.63f, 8.61f, -21.38f)
                verticalLineToRelative(-300.0f)
                quadToRelative(0.0f, -12.75f, -8.62f, -21.38f)
                quadToRelative(-8.63f, -8.62f, -21.39f, -8.62f)
                quadToRelative(-12.75f, 0.0f, -21.37f, 8.62f)
                quadToRelative(-8.61f, 8.63f, -8.61f, 21.38f)
                verticalLineToRelative(300.0f)
                quadToRelative(0.0f, 12.75f, 8.63f, 21.38f)
                quadToRelative(8.62f, 8.62f, 21.38f, 8.62f)
                close()
                moveTo(280.0f, 240.0f)
                verticalLineToRelative(520.0f)
                verticalLineToRelative(-520.0f)
                close()
            }
        }
        .build()
        return deleteIcon!!
    }

@Preview
@Composable
private fun Preview_DeleteIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = DeleteIcon, contentDescription = "")
    }
}
