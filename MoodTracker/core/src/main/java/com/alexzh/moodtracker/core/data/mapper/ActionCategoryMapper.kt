package com.alexzh.moodtracker.core.data.mapper

import com.alexzh.moodtracker.core.data.database.action.ActionCategoryEntity
import com.alexzh.moodtracker.core.domain.model.ActionCategory

fun ActionCategoryEntity.toDomain(): ActionCategory {
    return ActionCategory(
        id = id,
        name = name
    )
}

fun ActionCategory.toEntity(): ActionCategoryEntity {
    return ActionCategoryEntity(
        id = id,
        name = name,
    )
}

fun List<ActionCategoryEntity>.toDomainList(): List<ActionCategory> {
    return map { it.toDomain() }
}

fun List<ActionCategory>.toEntityList(): List<ActionCategoryEntity> {
    return map { it.toEntity() }
}