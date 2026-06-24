package com.alexzh.moodtracker.screenshottests.composepreview

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.ColorImage
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.alexzh.designsystem.component.media.AsyncImage
import com.alexzh.designsystem.core.theme.AppTheme
import com.android.tools.screenshot.PreviewTest

/**
 * Use the AsyncImage component from the Coil library instead of the component from the design system.
 */
@PreviewTest
@Preview(showBackground = true)
@Composable
fun AsyncImage_AsyncImagePreviewHandler() {
    AppTheme {
        val previewHandler = AsyncImagePreviewHandler {
            ColorImage(Color.Green.toArgb())
        }
        CompositionLocalProvider(
            LocalAsyncImagePreviewHandler provides previewHandler
        ) {
            AsyncImage(
                modifier = Modifier.size(200.dp),
                model = "content://media/external/images/media/1".toUri(),
                contentDescription = null,
            )
        }
    }
}

@PreviewTest
@Preview(showBackground = true)
@Composable
fun AsyncImage_InspectionMode() {
    AppTheme {
        AsyncImage(
            modifier = Modifier.size(200.dp),
            uri = "content://media/external/images/media/1".toUri(),
        )
    }
}
