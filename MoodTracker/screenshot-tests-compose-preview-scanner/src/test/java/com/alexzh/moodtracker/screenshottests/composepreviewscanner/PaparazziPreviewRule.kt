package com.alexzh.moodtracker.screenshottests.composepreviewscanner

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.HtmlReportWriter
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.Snapshot
import app.cash.paparazzi.SnapshotHandler
import app.cash.paparazzi.SnapshotVerifier
import app.cash.paparazzi.TestName
import app.cash.paparazzi.detectEnvironment
import com.android.ide.common.rendering.api.SessionParams
import com.android.resources.Density
import com.android.resources.NightMode
import com.android.resources.ScreenOrientation
import com.android.resources.ScreenRatio
import com.android.resources.ScreenRound
import com.android.resources.ScreenSize
import sergio.sastre.composable.preview.scanner.android.AndroidPreviewInfo
import sergio.sastre.composable.preview.scanner.android.device.DevicePreviewInfoParser
import sergio.sastre.composable.preview.scanner.android.device.domain.Device
import sergio.sastre.composable.preview.scanner.core.preview.ComposablePreview
import sergio.sastre.composable.preview.scanner.core.preview.getAnnotation
import kotlin.math.ceil

class Dimensions(
    val screenWidthInPx: Int,
    val screenHeightInPx: Int
)

object ScreenDimensions {
    fun dimensions(
        parsedDevice: Device,
        widthDp: Int,
        heightDp: Int
    ): Dimensions {
        val conversionFactor = parsedDevice.densityDpi / 160f
        val previewWidthInPx = ceil(widthDp * conversionFactor).toInt()
        val previewHeightInPx = ceil(heightDp * conversionFactor).toInt()
        return Dimensions(
            screenHeightInPx = when (heightDp > 0) {
                true -> previewHeightInPx
                false -> parsedDevice.dimensions.height.toInt()
            },
            screenWidthInPx = when (widthDp > 0) {
                true -> previewWidthInPx
                false -> parsedDevice.dimensions.width.toInt()
            }
        )
    }
}

object DeviceConfigBuilder {
    fun build(preview: AndroidPreviewInfo): DeviceConfig {
        val parsedDevice = DevicePreviewInfoParser.parse(preview.device)?.inPx() ?: return DeviceConfig()

        val dimensions = ScreenDimensions.dimensions(
            parsedDevice = parsedDevice,
            widthDp = preview.widthDp,
            heightDp = preview.heightDp
        )

        return DeviceConfig(
            screenHeight = dimensions.screenHeightInPx,
            screenWidth = dimensions.screenWidthInPx,
            density = Density(parsedDevice.densityDpi),
            xdpi = parsedDevice.densityDpi, // not 100% precise
            ydpi = parsedDevice.densityDpi, // not 100% precise
            size = ScreenSize.valueOf(parsedDevice.screenSize.name),
            ratio = ScreenRatio.valueOf(parsedDevice.screenRatio.name),
            screenRound = ScreenRound.valueOf(parsedDevice.shape.name),
            orientation = ScreenOrientation.valueOf(parsedDevice.orientation.name),
            locale = preview.locale.ifBlank { "en" },
            fontScale = preview.fontScale,
            nightMode = when (preview.uiMode and UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES) {
                true -> NightMode.NIGHT
                false -> NightMode.NOTNIGHT
            }
        )
    }
}

private val paparazziTestName =
    TestName(packageName = "Paparazzi", className = "Preview", methodName = "Test")

private class PreviewSnapshotVerifier(
    maxPercentDifference: Double
): SnapshotHandler {
    private val snapshotHandler = SnapshotVerifier(
        maxPercentDifference = maxPercentDifference
    )
    override fun newFrameHandler(
        snapshot: Snapshot,
        frameCount: Int,
        fps: Int
    ): SnapshotHandler.FrameHandler {
        val newSnapshot = Snapshot(
            name = snapshot.name,
            testName = paparazziTestName,
            timestamp = snapshot.timestamp,
            tags = snapshot.tags,
            file = snapshot.file,
        )
        return snapshotHandler.newFrameHandler(
            snapshot = newSnapshot,
            frameCount = frameCount,
            fps = fps
        )
    }

    override fun close() {
        snapshotHandler.close()
    }
}

private class PreviewHtmlReportWriter(
    maxPercentDifference: Double
): SnapshotHandler {
    private val snapshotHandler = HtmlReportWriter(
        maxPercentDifference = maxPercentDifference
    )
    override fun newFrameHandler(
        snapshot: Snapshot,
        frameCount: Int,
        fps: Int
    ): SnapshotHandler.FrameHandler {
        val newSnapshot = Snapshot(
            name = snapshot.name,
            testName = paparazziTestName,
            timestamp = snapshot.timestamp,
            tags = snapshot.tags,
            file = snapshot.file,
        )
        return snapshotHandler.newFrameHandler(
            snapshot = newSnapshot,
            frameCount = frameCount,
            fps = fps
        )
    }

    override fun close() {
        snapshotHandler.close()
    }
}

object PaparazziPreviewRule {
    const val UNDEFINED_API_LEVEL = -1
    const val MAX_API_LEVEL = 36

    fun createFor(
        preview: ComposablePreview<AndroidPreviewInfo>,
        maxPercentDifference: Double = 0.0
    ): Paparazzi {
        val previewInfo = preview.previewInfo
        val previewApiLevel = when(previewInfo.apiLevel == UNDEFINED_API_LEVEL) {
            true -> MAX_API_LEVEL
            false -> previewInfo.apiLevel
        }
        val tolerance = preview.getAnnotation<PaparazziConfig>()?.maxPercentDifference ?: 0.0
        return Paparazzi(
            environment = detectEnvironment().copy(compileSdkVersion = previewApiLevel),
            deviceConfig = DeviceConfigBuilder.build(previewInfo),
            supportsRtl = true,
            showSystemUi = previewInfo.showSystemUi,
            renderingMode = when {
                previewInfo.showSystemUi -> SessionParams.RenderingMode.NORMAL
                previewInfo.widthDp > 0 && previewInfo.heightDp > 0 -> SessionParams.RenderingMode.FULL_EXPAND
                else -> SessionParams.RenderingMode.SHRINK
            },
            snapshotHandler = when(System.getProperty("paparazzi.test.verify")?.toBoolean() == true) {
                true -> PreviewSnapshotVerifier(tolerance)
                false -> PreviewHtmlReportWriter(maxPercentDifference)
            },
            maxPercentDifference = tolerance
        )
    }
}

@Composable
fun SystemUiSize(
    widthInDp: Int,
    heightInDp: Int,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.size(width = widthInDp.dp, height = heightInDp.dp)
            .background(Color.White)
    ) {
        content()
    }
}

@Composable
fun PreviewBackground(
    showBackground: Boolean,
    backgroundColor: Long,
    content: @Composable () -> Unit
) {
    // Enable inspection mode to skip Activity-dependent code
    CompositionLocalProvider(LocalInspectionMode provides true) {
        when (showBackground) {
            false -> content()
            true -> {
                val color = when (backgroundColor != 0L) {
                    true -> Color(backgroundColor)
                    false -> Color.White
                }
                Box(Modifier.background(color)) {
                    content()
                }
            }
        }
    }
}