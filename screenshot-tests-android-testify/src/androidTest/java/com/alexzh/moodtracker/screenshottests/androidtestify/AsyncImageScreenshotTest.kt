package com.alexzh.moodtracker.screenshottests.androidtestify

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil3.annotation.DelicateCoilApi
import com.alexzh.designsystem.component.media.AsyncImage
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.screenshottests.common.FakeImageLoaderRule
import dev.testify.ComposableScreenshotRule
import dev.testify.annotation.ScreenshotInstrumentation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")
@RunWith(AndroidJUnit4::class)
class AsyncImageScreenshotTest {

    @get:Rule(order = 0)
    val fakeImageLoaderRule = FakeImageLoaderRule(
        { ApplicationProvider.getApplicationContext() }
    )

    @get:Rule(order = 1)
    val composableScreenshotRule = ComposableScreenshotRule()

    @OptIn(DelicateCoilApi::class)
    @ScreenshotInstrumentation
    @Test
    fun asyncImage_fakeImageLoader() {
        composableScreenshotRule
            .setCompose {
                AppTheme {
                    AsyncImage(
                        modifier = Modifier.size(200.dp),
                        uri = "content://media/external/images/media/1".toUri()
                    )
                }
            }
            .assertSame()
    }

    @OptIn(DelicateCoilApi::class)
    @ScreenshotInstrumentation
    @Test
    fun asyncImage_inspectionMode() {
        composableScreenshotRule
            .setCompose {
                CompositionLocalProvider(LocalInspectionMode provides true) {
                    AppTheme {
                        AsyncImage(
                            modifier = Modifier.size(200.dp),
                            uri = "content://media/external/images/media/1".toUri()
                        )
                    }
                }
            }
            .assertSame()
    }
}