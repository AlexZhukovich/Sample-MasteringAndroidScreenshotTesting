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

private var scheduleIcon: ImageVector? = null

val ScheduleIcon: ImageVector
    get() {
        if (scheduleIcon != null) {
            return scheduleIcon!!
        }
        scheduleIcon = Builder(name = "Schedule", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(510.0f, 467.85f)
                lineTo(510.0f, 310.0f)
                quadToRelative(0.0f, -12.75f, -8.63f, -21.38f)
                quadToRelative(-8.63f, -8.62f, -21.38f, -8.62f)
                quadToRelative(-12.76f, 0.0f, -21.37f, 8.62f)
                quadTo(450.0f, 297.25f, 450.0f, 310.0f)
                verticalLineToRelative(167.08f)
                quadToRelative(0.0f, 7.06f, 2.62f, 13.68f)
                quadToRelative(2.61f, 6.62f, 8.23f, 12.24f)
                lineToRelative(137.0f, 137.0f)
                quadToRelative(8.3f, 8.31f, 20.88f, 8.5f)
                quadToRelative(12.58f, 0.19f, 21.27f, -8.5f)
                reflectiveQuadToRelative(8.69f, -21.08f)
                quadToRelative(0.0f, -12.38f, -8.69f, -21.07f)
                lineToRelative(-130.0f, -130.0f)
                close()
                moveTo(480.07f, 860.0f)
                quadToRelative(-78.84f, 0.0f, -148.21f, -29.92f)
                reflectiveQuadToRelative(-120.68f, -81.21f)
                quadToRelative(-51.31f, -51.29f, -81.25f, -120.63f)
                quadTo(100.0f, 558.9f, 100.0f, 480.07f)
                quadToRelative(0.0f, -78.84f, 29.92f, -148.21f)
                reflectiveQuadToRelative(81.21f, -120.68f)
                quadToRelative(51.29f, -51.31f, 120.63f, -81.25f)
                quadTo(401.1f, 100.0f, 479.93f, 100.0f)
                quadToRelative(78.84f, 0.0f, 148.21f, 29.92f)
                reflectiveQuadToRelative(120.68f, 81.21f)
                quadToRelative(51.31f, 51.29f, 81.25f, 120.63f)
                quadTo(860.0f, 401.1f, 860.0f, 479.93f)
                quadToRelative(0.0f, 78.84f, -29.92f, 148.21f)
                reflectiveQuadToRelative(-81.21f, 120.68f)
                quadToRelative(-51.29f, 51.31f, -120.63f, 81.25f)
                quadTo(558.9f, 860.0f, 480.07f, 860.0f)
                close()
                moveTo(480.0f, 480.0f)
                close()
                moveTo(480.0f, 800.0f)
                quadToRelative(133.0f, 0.0f, 226.5f, -93.5f)
                reflectiveQuadTo(800.0f, 480.0f)
                quadToRelative(0.0f, -133.0f, -93.5f, -226.5f)
                reflectiveQuadTo(480.0f, 160.0f)
                quadToRelative(-133.0f, 0.0f, -226.5f, 93.5f)
                reflectiveQuadTo(160.0f, 480.0f)
                quadToRelative(0.0f, 133.0f, 93.5f, 226.5f)
                reflectiveQuadTo(480.0f, 800.0f)
                close()
            }
        }
        .build()
        return scheduleIcon!!
    }

@Preview
@Composable
private fun Preview_ScheduleIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = ScheduleIcon, contentDescription = "")
    }
}
