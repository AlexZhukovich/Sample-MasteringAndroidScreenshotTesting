package com.alexzh.moodtracker.common.ui.di

import com.alexzh.moodtracker.common.ui.model.LocalizedActionCategoryNameProvider
import com.alexzh.moodtracker.common.ui.model.LocalizedActionNameProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val actionLocalizationModule = module {
    single { LocalizedActionCategoryNameProvider(androidContext()) }
    single { LocalizedActionNameProvider(androidContext()) }
}