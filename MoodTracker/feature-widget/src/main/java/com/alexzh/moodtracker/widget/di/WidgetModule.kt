package com.alexzh.moodtracker.widget.di

import com.alexzh.moodtracker.widget.ui.configuration.WidgetConfigurationViewModel
import com.alexzh.moodtracker.widget.data.WidgetSettingsDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val widgetModule = module {
    single { WidgetSettingsDataSource(androidContext()) }

    viewModel { (appWidgetId: Int, onConfigurationComplete: (Boolean) -> Unit) ->
        WidgetConfigurationViewModel(
            widgetDataSource = get(),
            appWidgetId = appWidgetId,
            onConfigurationComplete = onConfigurationComplete
        )
    }
}