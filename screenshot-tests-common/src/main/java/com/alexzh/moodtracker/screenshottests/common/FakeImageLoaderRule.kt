package com.alexzh.moodtracker.screenshottests.common

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import coil3.ColorImage
import coil3.Image
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.annotation.DelicateCoilApi
import coil3.test.FakeImageLoaderEngine
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class FakeImageLoaderRule(
    private val contextProvider: () -> Context,
    val image: Image = ColorImage(Color.Green.toArgb())
): TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        val imageLoaderEngine = FakeImageLoaderEngine.Builder()
            .intercept({ request -> true }, image)
            .build()
        val imageLoader = ImageLoader.Builder(contextProvider())
            .components { add(imageLoaderEngine) }
            .build()
        SingletonImageLoader.setUnsafe(imageLoader)
    }

    @OptIn(DelicateCoilApi::class)
    override fun finished(description: Description?) {
        super.finished(description)
        SingletonImageLoader.reset()
    }
}