package com.alexzh.moodtracker.ui.feature.settings

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