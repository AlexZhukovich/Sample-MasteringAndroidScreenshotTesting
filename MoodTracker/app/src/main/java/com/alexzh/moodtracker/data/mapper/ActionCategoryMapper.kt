package com.alexzh.moodtracker.data.mapper

import com.alexzh.moodtracker.data.database.action.ActionCategoryEntity
import com.alexzh.moodtracker.domain.PastelAccentColor
import com.alexzh.moodtracker.domain.model.ActionCategory

fun ActionCategoryEntity.toDomain(): ActionCategory {
    return ActionCategory(
        id = id,
        name = name,
        color = PastelAccentColor.getPastelAccentColorsByName(
            colorName, 
            defaultColor = PastelAccentColor.GREY
        )
    )
}

fun ActionCategory.toEntity(): ActionCategoryEntity {
    return ActionCategoryEntity(
        id = id,
        name = name,
        colorName = color.name
    )
}

fun List<ActionCategoryEntity>.toDomainList(): List<ActionCategory> {
    return map { it.toDomain() }
}

fun List<ActionCategory>.toEntityList(): List<ActionCategoryEntity> {
    return map { it.toEntity() }
}