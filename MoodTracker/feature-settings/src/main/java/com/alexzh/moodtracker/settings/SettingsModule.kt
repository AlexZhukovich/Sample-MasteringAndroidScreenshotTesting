package com.alexzh.moodtracker.settings

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel {
        SettingsScreenViewModel(
            settingsDataSource = get(),
            appInfoProvider = get()
        )
    }
}