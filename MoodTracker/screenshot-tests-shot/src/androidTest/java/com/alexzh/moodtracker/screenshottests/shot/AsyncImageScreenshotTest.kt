package com.alexzh.moodtracker.screenshottests.shot

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexzh.designsystem.component.media.AsyncImage
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.screenshottests.common.FakeImageLoaderRule
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AsyncImageScreenshotTest : ScreenshotTest {

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
        compareScreenshot(composeTestRule.onRoot())
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
        compareScreenshot(composeTestRule.onRoot())
    }
}