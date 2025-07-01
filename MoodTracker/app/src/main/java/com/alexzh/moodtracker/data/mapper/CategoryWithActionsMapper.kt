package com.alexzh.moodtracker.data.mapper

import com.alexzh.moodtracker.data.database.action.ActionCategoryDetailsEntity
import com.alexzh.moodtracker.domain.model.ActionCategoryDetails

fun ActionCategoryDetailsEntity.toDomain(): ActionCategoryDetails {
    return ActionCategoryDetails(
        category = category.toDomain(),
        actions = actions.toDomainList()
    )
}

fun List<ActionCategoryDetailsEntity>.toDomainList(): List<ActionCategoryDetails> {
    return map { it.toDomain() }
}