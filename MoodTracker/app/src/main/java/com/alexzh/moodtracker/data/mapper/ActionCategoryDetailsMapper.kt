package com.alexzh.moodtracker.data.mapper

import com.alexzh.moodtracker.data.database.action.ActionCategoryDetailsEntity
import com.alexzh.moodtracker.domain.PastelAccentColor
import com.alexzh.moodtracker.domain.model.ActionCategoryDetails

fun ActionCategoryDetailsEntity.toDomain(): ActionCategoryDetails {
    return ActionCategoryDetails(
        id = category.id,
        name = category.name,
        color = PastelAccentColor.getPastelAccentColorsByName(
            category.colorName,
            defaultColor = PastelAccentColor.GREY
        ),
        actions = actions.toDomainList()
    )
}

fun List<ActionCategoryDetailsEntity>.toDomainList(): List<ActionCategoryDetails> {
    return map { it.toDomain() }
}