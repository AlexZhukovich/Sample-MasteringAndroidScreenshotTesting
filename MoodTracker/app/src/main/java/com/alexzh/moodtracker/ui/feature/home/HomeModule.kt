package com.alexzh.moodtracker.ui.feature.home

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeScreenViewModel(
            moodRecordDataSource = get(),
            dateProvider = get()
        )
    }
}