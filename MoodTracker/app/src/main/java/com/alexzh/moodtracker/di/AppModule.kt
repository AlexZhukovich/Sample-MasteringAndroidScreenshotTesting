package com.alexzh.moodtracker.di

import com.alexzh.moodtracker.core.DateProviderImpl
import com.alexzh.moodtracker.domain.provider.DateProvider
import com.alexzh.moodtracker.AppSettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<DateProvider> {
        DateProviderImpl(context = androidContext())
    }
    
    viewModel {
        AppSettingsViewModel(settingsDataSource = get())
    }
}