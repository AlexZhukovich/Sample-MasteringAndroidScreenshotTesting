package com.alexzh.moodtracker.actionmanagement

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val actionManagementModule = module {
    viewModel {
        ActionCategoriesScreenViewModel(
            actionCategoryDataSource = get(),
            actionDataSource = get()
        )
    }
}