package com.alexzh.moodtracker.core.domain.resolver

import android.net.Uri
import java.io.File

interface ImagePathResolver {
    fun resolveImageUri(imagePath: String): Uri

    fun getImageFile(imagePath: String): File
}