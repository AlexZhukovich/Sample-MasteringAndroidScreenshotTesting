package com.alexzh.moodtracker.di

import androidx.room.Room
import com.alexzh.moodtracker.data.database.ActionCategoryDataSourceImpl
import com.alexzh.moodtracker.data.database.ActionDataSourceImpl
import com.alexzh.moodtracker.data.database.MoodRecordDataSourceImpl
import com.alexzh.moodtracker.data.database.MoodTrackerDatabase
import com.alexzh.moodtracker.domain.datasource.ActionCategoryDataSource
import com.alexzh.moodtracker.domain.datasource.ActionDataSource
import com.alexzh.moodtracker.domain.datasource.MoodRecordDataSource
import com.alexzh.moodtracker.domain.datasource.SettingsDataSource
import com.alexzh.moodtracker.data.datastore.SettingsDataSourceImpl
import com.alexzh.moodtracker.data.initialization.DataInitializer
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
    single { get<MoodTrackerDatabase>().actionDao() }
    single { get<MoodTrackerDatabase>().actionCategoryDao() }
    single { get<MoodTrackerDatabase>().actionCategoryDetailsDao() }

    single<MoodRecordDataSource> {
        MoodRecordDataSourceImpl(
            moodRecordDao = get(),
            moodRecordWithActionsDao = get()
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

    single {
        DataInitializer(
            settingsDataSource = get(),
            actionCategoryDataSource = get(),
            actionDataSource = get()
        )
    }
}