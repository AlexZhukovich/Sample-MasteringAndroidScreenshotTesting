package com.alexzh.moodtracker.actionmanagement

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val actionManagementModule = module {
    viewModel {
        ActionCategoriesScreenViewModel(
            actionCategoryNameProvider = get(),
            actionNameProvider = get(),
            actionCategoryDataSource = get(),
            actionDataSource = get()
        )
    }
}