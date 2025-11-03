package com.alexzh.designsystem.component.media

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.alexzh.designsystem.R
import com.alexzh.designsystem.core.theme.AppTheme
import coil3.compose.AsyncImage as CoilAsyncImage

@Composable
fun AsyncImage(
    modifier: Modifier = Modifier,
    uri: Uri,
    contentDescription: String? = null,
    placeholder: Painter = painterResource(R.drawable.ic_image_placeholder),
    contentScale: ContentScale = ContentScale.Crop
) {
    if (LocalInspectionMode.current.not()) {
        CoilAsyncImage(
            modifier = modifier,
            model = uri,
            contentDescription = contentDescription,
            placeholder = placeholder,
            contentScale = contentScale
        )
    } else {
        Box(
            modifier = modifier
                .background(Color.Gray)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = placeholder,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
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