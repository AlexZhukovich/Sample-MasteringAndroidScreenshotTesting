package com.alexzh.moodtracker.core.di

import androidx.room.Room
import com.alexzh.moodtracker.core.data.database.ActionCategoryDataSourceImpl
import com.alexzh.moodtracker.core.data.database.ActionDataSourceImpl
import com.alexzh.moodtracker.core.data.database.MoodRecordDataSourceImpl
import com.alexzh.moodtracker.core.data.database.MoodTrackerDatabase
import com.alexzh.moodtracker.core.data.resolver.ImagePathResolverImpl
import com.alexzh.moodtracker.core.domain.datasource.ActionCategoryDataSource
import com.alexzh.moodtracker.core.domain.datasource.ActionDataSource
import com.alexzh.moodtracker.core.domain.datasource.MoodRecordDataSource
import com.alexzh.moodtracker.core.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.core.domain.resolver.ImagePathResolver
import com.alexzh.moodtracker.core.data.datastore.SettingsDataSourceImpl
import com.alexzh.moodtracker.core.data.initialization.DataInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = MoodTrackerDatabase::class.java,
            name = "mood_tracker_database"
        ).build()
    }

    single { get<MoodTrackerDatabase>().moodRecordDao() }
    single { get<MoodTrackerDatabase>().moodRecordWithActionsDao() }
    single { get<MoodTrackerDatabase>().moodPhotoDao() }
    single { get<MoodTrackerDatabase>().actionDao() }
    single { get<MoodTrackerDatabase>().actionCategoryDao() }
    single { get<MoodTrackerDatabase>().actionCategoryDetailsDao() }

    single<MoodRecordDataSource> {
        MoodRecordDataSourceImpl(
            context = androidContext(),
            imagePathResolver = get(),
            moodRecordDao = get(),
            moodRecordWithActionsDao = get(),
            moodPhotoDao = get()
        )
    }
    single<ActionDataSource> {
        ActionDataSourceImpl(
            actionDao = get()
        )
    }
    single<ActionCategoryDataSource> {
        ActionCategoryDataSourceImpl(
            actionCategoryDao = get(),
            actionCategoryDetailsDao = get()
        )
    }
    single<SettingsDataSource> {
        SettingsDataSourceImpl(context = androidContext())
    }

    single<ImagePathResolver> {
        ImagePathResolverImpl(context = androidContext())
    }

    single {
        DataInitializer(
            settingsDataSource = get(),
            actionCategoryDataSource = get(),
            actionDataSource = get()
        )
    }
}