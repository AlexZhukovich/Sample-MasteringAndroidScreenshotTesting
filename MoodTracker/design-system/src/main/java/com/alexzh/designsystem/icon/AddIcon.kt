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

private var addIcon: ImageVector? = null

val AddIcon: ImageVector
    get() {
        if (addIcon != null) {
            return addIcon!!
        }
        addIcon = Builder(name = "Add", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(479.99f, 820.0f)
                quadToRelative(-12.76f, 0.0f, -21.37f, -8.63f)
                quadTo(450.0f, 802.75f, 450.0f, 790.0f)
                verticalLineToRelative(-280.0f)
                lineTo(170.0f, 510.0f)
                quadToRelative(-12.75f, 0.0f, -21.37f, -8.63f)
                quadToRelative(-8.63f, -8.63f, -8.63f, -21.38f)
                quadToRelative(0.0f, -12.76f, 8.63f, -21.37f)
                quadTo(157.25f, 450.0f, 170.0f, 450.0f)
                horizontalLineToRelative(280.0f)
                verticalLineToRelative(-280.0f)
                quadToRelative(0.0f, -12.75f, 8.63f, -21.37f)
                quadToRelative(8.63f, -8.63f, 21.38f, -8.63f)
                quadToRelative(12.76f, 0.0f, 21.37f, 8.63f)
                quadTo(510.0f, 157.25f, 510.0f, 170.0f)
                verticalLineToRelative(280.0f)
                horizontalLineToRelative(280.0f)
                quadToRelative(12.75f, 0.0f, 21.37f, 8.63f)
                quadToRelative(8.63f, 8.63f, 8.63f, 21.38f)
                quadToRelative(0.0f, 12.76f, -8.63f, 21.37f)
                quadTo(802.75f, 510.0f, 790.0f, 510.0f)
                lineTo(510.0f, 510.0f)
                verticalLineToRelative(280.0f)
                quadToRelative(0.0f, 12.75f, -8.63f, 21.37f)
                quadToRelative(-8.63f, 8.63f, -21.38f, 8.63f)
                close()
            }
        }
        .build()
        return addIcon!!
    }

@Preview
@Composable
private fun Preview_AddIcon() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = AddIcon, contentDescription = "")
    }
}
