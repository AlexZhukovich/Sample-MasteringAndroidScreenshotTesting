package com.alexzh.moodtracker.data.mapper

import com.alexzh.moodtracker.data.database.action.ActionCategoryDetailsEntity
import com.alexzh.moodtracker.domain.model.ActionCategoryDetails

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