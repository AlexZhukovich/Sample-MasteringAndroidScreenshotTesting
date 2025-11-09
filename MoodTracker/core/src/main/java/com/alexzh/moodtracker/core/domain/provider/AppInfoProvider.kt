package com.alexzh.moodtracker.core.domain.provider

import com.alexzh.moodtracker.core.domain.model.AppInfo

interface AppInfoProvider {
    fun getAppInfo(): AppInfo
}