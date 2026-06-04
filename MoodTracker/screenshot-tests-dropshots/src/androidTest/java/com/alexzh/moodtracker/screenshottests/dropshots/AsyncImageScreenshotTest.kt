package com.alexzh.moodtracker.screenshottests.dropshots

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import com.alexzh.designsystem.component.media.AsyncImage
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.screenshottests.common.FakeImageLoaderRule
import com.dropbox.dropshots.Dropshots
import org.junit.Rule
import org.junit.Test

class AsyncImageScreenshotTest {

    companion object Companion {
        const val FILE_PATH = "design-system"
    }

    @get:Rule(order = 0)
    val fakeImageLoaderRule = FakeImageLoaderRule(
        { ApplicationProvider.getApplicationContext() }
    )

    @get:Rule(order = 1)
    val dropshots = Dropshots()

    @get:Rule(order = 2)
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

        dropshots.assertSnapshot(
            bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap(),
            name = "asyncImage_fakeImageLoader",
            filePath = FILE_PATH
        )
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

        dropshots.assertSnapshot(
            bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap(),
            name = "asyncImage_inspectionMode",
            filePath = FILE_PATH
        )
    }
}