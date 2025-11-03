package com.alexzh.designsystem.component.media

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.designsystem.R

@Composable
fun PhotoThumbnailGrid(
    modifier: Modifier = Modifier,
    photos: List<Uri>,
    thumbnailSize: Dp,
    spacing: Dp = 8.dp,
    contentScale: ContentScale = ContentScale.Crop,
    editable: Boolean = false,
    removeIcon: Painter = painterResource(R.drawable.ic_close),
    removeIconContentDescription: String = stringResource(R.string.photoThumbnailGrid_removePhoto_label),
    onRemove: ((Int) -> Unit)? = null,
    onAddPhoto: (() -> Unit)? = null,
    addIcon: Painter = painterResource(R.drawable.ic_add),
    addIconContentDescription: String = stringResource(R.string.photoThumbnailGrid_addPhoto_label),
    maxPhotos: Int? = null
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        photos.forEachIndexed { index, uri ->
            PhotoThumbnail(
                uri = uri,
                contentDescription = null,
                size = thumbnailSize,
                contentScale = contentScale,
                onRemove = if (editable && onRemove != null) {
                    { onRemove(index) }
                } else null,
                removeIcon = removeIcon,
                removeIconContentDescription = removeIconContentDescription
            )
        }

        if (editable && onAddPhoto != null &&
            (maxPhotos == null || photos.size < maxPhotos)) {
            AddPhotoButton(
                onClick = onAddPhoto,
                size = thumbnailSize,
                icon = addIcon,
                contentDescription = addIconContentDescription
            )
        }
    }
}

@Composable
private fun AddPhotoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp,
    iconSize: Dp = 32.dp,
    icon: Painter,
    contentDescription: String
) {
    OutlinedCard(
        modifier = modifier
            .size(size)
            .aspectRatio(1f),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
private fun PhotoThumbnail(
    modifier: Modifier = Modifier,
    uri: Uri,
    contentDescription: String?,
    size: Dp,
    contentScale: ContentScale = ContentScale.Crop,
    onRemove: (() -> Unit)? = null,
    removeIcon: Painter,
    removeIconContentDescription: String
) {
    Box(
        modifier = modifier
            .size(size)
            .aspectRatio(1f)
    ) {
        val imageModifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)

        AsyncImage(
            uri = uri,
            contentDescription = contentDescription,
            modifier = imageModifier,
            contentScale = contentScale
        )

        onRemove?.let { removeCallback ->
            Box(
                modifier = Modifier
                    .padding(top = 2.dp, end = 2.dp)
                    .align(Alignment.TopEnd)
                    .size(30.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable(onClick = removeCallback),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = removeIcon,
                    contentDescription = removeIconContentDescription,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview_PhotoThumbnailGrid_MaxPhotos() {
    AppTheme {
        PhotoThumbnailGrid(
            photos = listOf(
                "content://media/external/images/media/1".toUri(),
                "content://media/external/images/media/2".toUri(),
                "content://media/external/images/media/3".toUri()
            ),
            thumbnailSize = 80.dp,
            editable = false,
            maxPhotos = 3
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_PhotoThumbnailGrid_Editable_OnePhoto() {
    AppTheme {
        PhotoThumbnailGrid(
            photos = listOf(
                "content://media/external/images/media/1".toUri()
            ),
            thumbnailSize = 80.dp,
            editable = true,
            onRemove = {},
            onAddPhoto = {},
            maxPhotos = 3
        )
    }
}