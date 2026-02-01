package com.alexzh.moodtracker.di

import com.alexzh.moodtracker.core.data.provider.DateProviderImpl
import com.alexzh.moodtracker.core.domain.provider.DateProvider
import com.alexzh.moodtracker.AppSettingsViewModel
import com.alexzh.moodtracker.core.data.provider.AppInfoProviderImpl
import com.alexzh.moodtracker.core.domain.provider.AppInfoProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<DateProvider> {
        DateProviderImpl(context = androidContext())
    }

    single<AppInfoProvider> {
        AppInfoProviderImpl(context = androidContext())
    }

    viewModel {
        AppSettingsViewModel(settingsDataSource = get())
    }
}