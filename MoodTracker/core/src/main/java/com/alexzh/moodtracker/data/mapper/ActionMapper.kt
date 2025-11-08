package com.alexzh.moodtracker.data.mapper

import com.alexzh.moodtracker.data.database.action.ActionEntity
import com.alexzh.moodtracker.domain.model.Action

fun ActionEntity.toDomain(): Action {
    return Action(
        id = id,
        title = title,
        categoryId = categoryId
    )
}

fun Action.toEntity(): ActionEntity {
    return ActionEntity(
        id = id,
        title = title,
        categoryId = categoryId
    )
}

fun List<ActionEntity>.toDomainList(): List<Action> {
    return map { it.toDomain() }
}

fun List<Action>.toEntityList(): List<ActionEntity> {
    return map { it.toEntity() }
}