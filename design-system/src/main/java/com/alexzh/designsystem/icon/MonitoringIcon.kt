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

private var monitoringIcon: ImageVector? = null

val MonitoringIcon: ImageVector
    get() {
        if (monitoringIcon != null) {
            return monitoringIcon!!
        }
        monitoringIcon = Builder(name = "Monitoring", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(159.99f, 830.0f)
                quadToRelative(-12.76f, 0.0f, -21.37f, -8.63f)
                quadTo(130.0f, 812.75f, 130.0f, 800.0f)
                verticalLineToRelative(-35.77f)
                quadToRelative(0.0f, -12.75f, 8.63f, -21.37f)
                quadToRelative(8.63f, -8.63f, 21.38f, -8.63f)
                quadToRelative(12.76f, 0.0f, 21.37f, 8.63f)
                quadToRelative(8.62f, 8.62f, 8.62f, 21.37f)
                lineTo(190.0f, 800.0f)
                quadToRelative(0.0f, 12.75f, -8.63f, 21.37f)
                quadToRelative(-8.63f, 8.63f, -21.38f, 8.63f)
                close()
                moveTo(319.99f, 830.0f)
                quadToRelative(-12.76f, 0.0f, -21.37f, -8.63f)
                quadTo(290.0f, 812.75f, 290.0f, 800.0f)
                verticalLineToRelative(-215.77f)
                quadToRelative(0.0f, -12.75f, 8.63f, -21.37f)
                quadToRelative(8.63f, -8.63f, 21.38f, -8.63f)
                quadToRelative(12.76f, 0.0f, 21.37f, 8.63f)
                quadToRelative(8.62f, 8.62f, 8.62f, 21.37f)
                lineTo(350.0f, 800.0f)
                quadToRelative(0.0f, 12.75f, -8.63f, 21.37f)
                quadToRelative(-8.63f, 8.63f, -21.38f, 8.63f)
                close()
                moveTo(479.99f, 830.0f)
                quadToRelative(-12.76f, 0.0f, -21.37f, -8.63f)
                quadTo(450.0f, 812.75f, 450.0f, 800.0f)
                verticalLineToRelative(-135.77f)
                quadToRelative(0.0f, -12.75f, 8.63f, -21.37f)
                quadToRelative(8.63f, -8.63f, 21.38f, -8.63f)
                quadToRelative(12.76f, 0.0f, 21.37f, 8.63f)
                quadToRelative(8.62f, 8.62f, 8.62f, 21.37f)
                lineTo(510.0f, 800.0f)
                quadToRelative(0.0f, 12.75f, -8.63f, 21.37f)
                quadToRelative(-8.63f, 8.63f, -21.38f, 8.63f)
                close()
                moveTo(639.99f, 830.0f)
                quadToRelative(-12.76f, 0.0f, -21.37f, -8.63f)
                quadTo(610.0f, 812.75f, 610.0f, 800.0f)
                verticalLineToRelative(-195.77f)
                quadToRelative(0.0f, -12.75f, 8.63f, -21.37f)
                quadToRelative(8.63f, -8.63f, 21.38f, -8.63f)
                quadToRelative(12.76f, 0.0f, 21.37f, 8.63f)
                quadToRelative(8.62f, 8.62f, 8.62f, 21.37f)
                lineTo(670.0f, 800.0f)
                quadToRelative(0.0f, 12.75f, -8.63f, 21.37f)
                quadToRelative(-8.63f, 8.63f, -21.38f, 8.63f)
                close()
                moveTo(799.99f, 830.0f)
                quadToRelative(-12.76f, 0.0f, -21.37f, -8.63f)
                quadTo(770.0f, 812.75f, 770.0f, 800.0f)
                verticalLineToRelative(-355.77f)
                quadToRelative(0.0f, -12.75f, 8.63f, -21.37f)
                quadToRelative(8.63f, -8.63f, 21.38f, -8.63f)
                quadToRelative(12.76f, 0.0f, 21.37f, 8.63f)
                quadToRelative(8.62f, 8.62f, 8.62f, 21.37f)
                lineTo(830.0f, 800.0f)
                quadToRelative(0.0f, 12.75f, -8.63f, 21.37f)
                quadToRelative(-8.63f, 8.63f, -21.38f, 8.63f)
                close()
                moveTo(560.0f, 468.23f)
                quadToRelative(-14.08f, 0.0f, -27.22f, -5.4f)
                quadToRelative(-13.14f, -5.4f, -24.01f, -15.29f)
                lineToRelative(-99.92f, -99.93f)
                quadToRelative(-3.46f, -3.46f, -8.85f, -3.46f)
                reflectiveQuadToRelative(-8.85f, 3.46f)
                lineTo(181.08f, 557.69f)
                quadToRelative(-8.93f, 8.92f, -21.39f, 8.81f)
                quadToRelative(-12.46f, -0.12f, -21.38f, -9.42f)
                quadToRelative(-8.31f, -8.93f, -7.94f, -21.24f)
                quadToRelative(0.37f, -12.32f, 8.55f, -20.53f)
                lineToRelative(209.85f, -209.85f)
                quadToRelative(10.87f, -10.79f, 24.01f, -15.74f)
                quadToRelative(13.14f, -4.95f, 27.22f, -4.95f)
                quadToRelative(14.08f, 0.0f, 27.67f, 4.95f)
                quadToRelative(13.59f, 4.95f, 23.56f, 15.74f)
                lineToRelative(99.92f, 99.93f)
                quadToRelative(3.46f, 3.46f, 8.85f, 3.46f)
                reflectiveQuadToRelative(8.85f, -3.46f)
                lineToRelative(210.07f, -210.08f)
                quadToRelative(8.93f, -8.92f, 21.39f, -8.81f)
                quadToRelative(12.46f, 0.12f, 21.38f, 9.42f)
                quadToRelative(8.31f, 8.93f, 7.94f, 21.24f)
                quadToRelative(-0.37f, 12.32f, -8.55f, 20.53f)
                lineTo(611.23f, 447.54f)
                quadToRelative(-9.97f, 9.89f, -23.56f, 15.29f)
                quadToRelative(-13.59f, 5.4f, -27.67f, 5.4f)
                close()
            }
        }
        .build()
        return monitoringIcon!!
    }

@Preview
@Composable
private fun Preview_MonitoringIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = MonitoringIcon, contentDescription = "")
    }
}
