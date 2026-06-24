package com.alexzh.designsystem.component.media

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.icon.ImagePlaceholderIcon
import coil3.compose.AsyncImage as CoilAsyncImage

@Composable
fun AsyncImage(
    modifier: Modifier = Modifier,
    uri: Uri,
    contentDescription: String? = null,
    placeholder: ImageVector = ImagePlaceholderIcon,
    contentScale: ContentScale = ContentScale.Crop
) {
    if (LocalInspectionMode.current.not()) {
        CoilAsyncImage(
            modifier = modifier,
            model = uri,
            contentDescription = contentDescription,
            placeholder = rememberVectorPainter(placeholder),
            contentScale = contentScale
        )
    } else {
        Image(
            modifier = modifier.fillMaxSize()
                .background(Color.Green),
            imageVector = placeholder,
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Preview
@Composable
fun Preview_AsyncImage() {
    AppTheme {
        AsyncImage(
            modifier = Modifier.size(200.dp),
            uri = "content://media/external/images/media/1".toUri(),
        )
    }
}