package com.alexzh.moodtracker.screenshottests.roborazzi

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import com.alexzh.designsystem.component.media.AsyncImage
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.screenshottests.common.FakeImageLoaderRule
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel4, sdk = [33])
class AsyncImageScreenshotTest {

    @get:Rule(order = 0)
    val fakeImageLoaderRule = FakeImageLoaderRule(
        { ApplicationProvider.getApplicationContext() }
    )

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun asyncImage_fakeImageLoader() {
        composeTestRule.setContent {
            AppTheme {
                AsyncImage(
                    modifier = Modifier.size(200.dp),
                    uri = "content://media/external/images/media/1".toUri()
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun asyncImage_inspectionMode() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                AppTheme {
                    AsyncImage(
                        modifier = Modifier.size(200.dp),
                        uri = "content://media/external/images/media/1".toUri()
                    )
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}