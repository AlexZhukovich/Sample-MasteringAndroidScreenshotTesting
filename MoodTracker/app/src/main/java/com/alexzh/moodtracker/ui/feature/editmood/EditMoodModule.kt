package com.alexzh.moodtracker.ui.feature.editmood

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val editMoodModule = module {
    viewModel {
        EditMoodScreenViewModel(
            moodRecordDataSource = get(),
            imagePathResolver = get(),
            actionCategoryDataSource = get(),
            dateProvider = get(),
            savedStateHandle = get(),
        )
    }
}