package com.alexzh.moodtracker.home

import com.alexzh.moodtracker.home.edit.mood.EditMoodScreenViewModel
import com.alexzh.moodtracker.home.overview.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeScreenViewModel(
            moodRecordDataSource = get(),
            imagePathResolver = get(),
            settingsDataSource = get(),
            dateProvider = get()
        )
    }
    viewModel {
        EditMoodScreenViewModel(
            moodRecordDataSource = get(),
            imagePathResolver = get(),
            settingsDataSource = get(),
            actionCategoryDataSource = get(),
            dateProvider = get(),
            savedStateHandle = get(),
        )
    }
}