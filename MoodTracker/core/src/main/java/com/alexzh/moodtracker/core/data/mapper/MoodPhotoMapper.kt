package com.alexzh.moodtracker.core.data.mapper

import com.alexzh.moodtracker.core.data.database.moodphoto.MoodPhotoEntity
import com.alexzh.moodtracker.core.domain.model.MoodPhoto

fun MoodPhotoEntity.toDomain(): MoodPhoto {
    return MoodPhoto(
        id = id,
        photoPath = photoPath
    )
}