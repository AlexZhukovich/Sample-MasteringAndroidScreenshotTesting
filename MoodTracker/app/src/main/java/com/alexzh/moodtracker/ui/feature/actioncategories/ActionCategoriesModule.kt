package com.alexzh.moodtracker.ui.feature.actioncategories

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val actionCategoriesModule = module {
    viewModel {
        ActionCategoriesScreenViewModel(
            actionCategoryDataSource = get()
        )
    }
}