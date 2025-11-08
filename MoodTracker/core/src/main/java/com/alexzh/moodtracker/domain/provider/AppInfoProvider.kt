package com.alexzh.moodtracker.domain.provider

import com.alexzh.moodtracker.domain.model.AppInfo

interface AppInfoProvider {
    fun getAppInfo(): AppInfo
}