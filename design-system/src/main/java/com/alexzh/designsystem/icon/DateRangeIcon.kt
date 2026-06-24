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

private var dateRangeIcon: ImageVector? = null

val DateRangeIcon: ImageVector
    get() {
        if (dateRangeIcon != null) {
            return dateRangeIcon!!
        }
        dateRangeIcon = Builder(name = "DateRange", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(320.0f, 557.69f)
                quadToRelative(-14.69f, 0.0f, -25.04f, -10.34f)
                quadToRelative(-10.34f, -10.35f, -10.34f, -25.04f)
                reflectiveQuadToRelative(10.34f, -25.04f)
                quadToRelative(10.35f, -10.35f, 25.04f, -10.35f)
                reflectiveQuadToRelative(25.04f, 10.35f)
                quadToRelative(10.34f, 10.35f, 10.34f, 25.04f)
                reflectiveQuadToRelative(-10.34f, 25.04f)
                quadToRelative(-10.35f, 10.34f, -25.04f, 10.34f)
                close()
                moveTo(480.0f, 557.69f)
                quadToRelative(-14.69f, 0.0f, -25.04f, -10.34f)
                quadToRelative(-10.34f, -10.35f, -10.34f, -25.04f)
                reflectiveQuadToRelative(10.34f, -25.04f)
                quadToRelative(10.35f, -10.35f, 25.04f, -10.35f)
                reflectiveQuadToRelative(25.04f, 10.35f)
                quadToRelative(10.34f, 10.35f, 10.34f, 25.04f)
                reflectiveQuadToRelative(-10.34f, 25.04f)
                quadToRelative(-10.35f, 10.34f, -25.04f, 10.34f)
                close()
                moveTo(640.0f, 557.69f)
                quadToRelative(-14.69f, 0.0f, -25.04f, -10.34f)
                quadToRelative(-10.34f, -10.35f, -10.34f, -25.04f)
                reflectiveQuadToRelative(10.34f, -25.04f)
                quadToRelative(10.35f, -10.35f, 25.04f, -10.35f)
                reflectiveQuadToRelative(25.04f, 10.35f)
                quadToRelative(10.34f, 10.35f, 10.34f, 25.04f)
                reflectiveQuadToRelative(-10.34f, 25.04f)
                quadToRelative(-10.35f, 10.34f, -25.04f, 10.34f)
                close()
                moveTo(212.31f, 860.0f)
                quadTo(182.0f, 860.0f, 161.0f, 839.0f)
                quadToRelative(-21.0f, -21.0f, -21.0f, -51.31f)
                verticalLineToRelative(-535.38f)
                quadTo(140.0f, 222.0f, 161.0f, 201.0f)
                quadToRelative(21.0f, -21.0f, 51.31f, -21.0f)
                horizontalLineToRelative(55.38f)
                verticalLineToRelative(-53.85f)
                quadToRelative(0.0f, -13.15f, 8.81f, -21.96f)
                quadToRelative(8.81f, -8.8f, 21.96f, -8.8f)
                quadToRelative(13.16f, 0.0f, 21.96f, 8.8f)
                quadToRelative(8.81f, 8.81f, 8.81f, 21.96f)
                lineTo(329.23f, 180.0f)
                horizontalLineToRelative(303.08f)
                verticalLineToRelative(-54.61f)
                quadToRelative(0.0f, -12.77f, 8.61f, -21.39f)
                quadToRelative(8.62f, -8.61f, 21.39f, -8.61f)
                quadToRelative(12.77f, 0.0f, 21.38f, 8.61f)
                quadToRelative(8.62f, 8.62f, 8.62f, 21.39f)
                lineTo(692.31f, 180.0f)
                horizontalLineToRelative(55.38f)
                quadTo(778.0f, 180.0f, 799.0f, 201.0f)
                quadToRelative(21.0f, 21.0f, 21.0f, 51.31f)
                verticalLineToRelative(535.38f)
                quadTo(820.0f, 818.0f, 799.0f, 839.0f)
                quadToRelative(-21.0f, 21.0f, -51.31f, 21.0f)
                lineTo(212.31f, 860.0f)
                close()
                moveTo(212.31f, 800.0f)
                horizontalLineToRelative(535.38f)
                quadToRelative(4.62f, 0.0f, 8.46f, -3.85f)
                quadToRelative(3.85f, -3.84f, 3.85f, -8.46f)
                verticalLineToRelative(-375.38f)
                lineTo(200.0f, 412.31f)
                verticalLineToRelative(375.38f)
                quadToRelative(0.0f, 4.62f, 3.85f, 8.46f)
                quadToRelative(3.84f, 3.85f, 8.46f, 3.85f)
                close()
                moveTo(200.0f, 352.31f)
                horizontalLineToRelative(560.0f)
                verticalLineToRelative(-100.0f)
                quadToRelative(0.0f, -4.62f, -3.85f, -8.46f)
                quadToRelative(-3.84f, -3.85f, -8.46f, -3.85f)
                lineTo(212.31f, 240.0f)
                quadToRelative(-4.62f, 0.0f, -8.46f, 3.85f)
                quadToRelative(-3.85f, 3.84f, -3.85f, 8.46f)
                verticalLineToRelative(100.0f)
                close()
                moveTo(200.0f, 352.31f)
                lineTo(200.0f, 240.0f)
                verticalLineToRelative(112.31f)
                close()
            }
        }
        .build()
        return dateRangeIcon!!
    }

@Preview
@Composable
private fun Preview_DateRangeIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = DateRangeIcon, contentDescription = "")
    }
}
