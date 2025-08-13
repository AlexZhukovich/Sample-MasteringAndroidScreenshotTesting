package com.alexzh.moodtracker.ui.feature.actioncategorydetails

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val actionCategoryDetailsModule = module {
    viewModel {
        ActionCategoryDetailsScreenViewModel(
            actionCategoryDataSource = get(),
            actionDataSource = get(),
            savedStateHandle = get()
        )
    }
}