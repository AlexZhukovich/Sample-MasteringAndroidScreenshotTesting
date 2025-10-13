package com.alexzh.moodtracker.data.resolver

import android.content.Context
import android.net.Uri
import com.alexzh.moodtracker.domain.resolver.ImagePathResolver
import java.io.File

class ImagePathResolverImpl(
    private val context: Context
) : ImagePathResolver {

    private val imageDirectory: File
        get() = File(context.filesDir, "photos").also { it.mkdirs() }

    override fun resolveImageUri(imagePath: String): Uri {
        return Uri.fromFile(getImageFile(imagePath))
    }

    override fun getImageFile(imagePath: String): File {
        return File(imageDirectory, imagePath)
    }
}