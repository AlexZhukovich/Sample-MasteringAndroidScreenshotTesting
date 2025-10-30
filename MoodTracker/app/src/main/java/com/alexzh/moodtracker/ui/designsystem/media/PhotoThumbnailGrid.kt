package com.alexzh.moodtracker.ui.designsystem.media

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.theme.AppTheme

@Composable
fun PhotoThumbnailGrid(
    modifier: Modifier = Modifier,
    photos: List<Uri>,
    thumbnailSize: Dp,
    spacing: Dp = 8.dp,
    contentScale: ContentScale = ContentScale.Crop,
    editable: Boolean = false,
    onRemove: ((Int) -> Unit)? = null,
    maxPhotos: Int? = null,
    photoPicker: ManagedActivityResultLauncher<String, Uri?>? = null
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
                } else null
            )
        }

        if (editable && photoPicker != null &&
            (maxPhotos == null || photos.size < maxPhotos)) {
            AddPhotoButton(
                onClick = { photoPicker.launch("image/*") },
                size = thumbnailSize
            )
        }
    }
}

@Composable
private fun AddPhotoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp,
    iconSize: Dp = 32.dp
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
                painter = painterResource(R.drawable.ic_add),
                contentDescription = stringResource(R.string.editMoodScreen_addPhoto_label),
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
    onRemove: (() -> Unit)? = null
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
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = stringResource(R.string.editMoodScreen_removePhoto_label),
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
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { _: Uri? -> }

    AppTheme {
        PhotoThumbnailGrid(
            photos = listOf(
                "content://media/external/images/media/1".toUri()
            ),
            thumbnailSize = 80.dp,
            editable = true,
            onRemove = {},
            maxPhotos = 3,
            photoPicker = photoPicker
        )
    }
}