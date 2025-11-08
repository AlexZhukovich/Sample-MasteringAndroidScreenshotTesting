package com.alexzh.moodtracker.data.mapper

import com.alexzh.moodtracker.data.database.moodphoto.MoodPhotoEntity
import com.alexzh.moodtracker.domain.model.MoodPhoto

fun MoodPhotoEntity.toDomain(): MoodPhoto {
    return MoodPhoto(
        id = id,
        photoPath = photoPath
    )
}