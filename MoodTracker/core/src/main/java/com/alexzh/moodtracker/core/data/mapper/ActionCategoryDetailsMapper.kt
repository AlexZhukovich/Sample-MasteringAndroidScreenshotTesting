package com.alexzh.moodtracker.core.data.mapper

import com.alexzh.moodtracker.core.data.database.action.ActionCategoryDetailsEntity
import com.alexzh.moodtracker.core.domain.model.ActionCategoryDetails

fun ActionCategoryDetailsEntity.toDomain(): ActionCategoryDetails {
    return ActionCategoryDetails(
        id = category.id,
        name = category.name,
        actions = actions.toDomainList()
    )
}

fun List<ActionCategoryDetailsEntity>.toDomainList(): List<ActionCategoryDetails> {
    return map { it.toDomain() }
}