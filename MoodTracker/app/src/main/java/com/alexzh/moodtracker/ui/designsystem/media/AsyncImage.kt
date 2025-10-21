package com.alexzh.moodtracker.ui.designsystem.media

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage as CoilAsyncImage

@Composable
fun AsyncImage(
    modifier: Modifier = Modifier,
    uri: Uri,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop
) {
    CoilAsyncImage(
        model = uri,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}