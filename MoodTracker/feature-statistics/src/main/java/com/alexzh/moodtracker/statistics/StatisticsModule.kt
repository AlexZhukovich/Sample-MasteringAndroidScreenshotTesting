package com.alexzh.moodtracker.statistics

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val statisticsModule = module {
    viewModel {
        StatisticsScreenViewModel(
            moodRepository = get(),
            dateProvider = get(),
            settingsDataSource = get()
        )
    }
}