package com.alexzh.moodtracker.ui.feature.previewmood

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val previewMoodModule = module {
    viewModel {
        PreviewMoodScreenViewModel(
            moodRecordDataSource = get(),
            savedStateHandle = get(),
        )
    }
}