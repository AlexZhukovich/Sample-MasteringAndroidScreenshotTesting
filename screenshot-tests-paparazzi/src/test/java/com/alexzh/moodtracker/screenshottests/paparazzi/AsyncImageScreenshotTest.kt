package com.alexzh.moodtracker.screenshottests.paparazzi

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexzh.designsystem.component.media.AsyncImage
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.screenshottests.common.FakeImageLoaderRule
import com.android.ide.common.rendering.api.SessionParams.RenderingMode
import org.junit.Rule
import org.junit.Test

class AsyncImageScreenshotTest {

    @get:Rule(order = 0)
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_4,
        renderingMode = RenderingMode.SHRINK
    )

    @get:Rule(order = 1)
    val fakeImageLoaderRule = FakeImageLoaderRule(contextProvider = { paparazzi.context })

    @Test
    fun asyncImage_fakeImageLoader() {
        paparazzi.snapshot {
            AppTheme {
                AsyncImage(
                    modifier = Modifier.size(200.dp),
                    uri = "content://media/external/images/media/1".toUri()
                )
            }
        }
    }

    @Test
    fun asyncImage_inspectionMode() {
        paparazzi.snapshot {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                AppTheme {
                    AsyncImage(
                        modifier = Modifier.size(200.dp),
                        uri = "content://media/external/images/media/1".toUri()
                    )
                }
            }
        }
    }
}