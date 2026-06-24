package com.alexzh.moodtracker.core.data.provider

import android.content.Context
import com.alexzh.moodtracker.core.domain.model.AppInfo
import com.alexzh.moodtracker.core.domain.provider.AppInfoProvider

class AppInfoProviderImpl(
    val context: Context
) : AppInfoProvider {
    override fun getAppInfo(): AppInfo {
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName, 0)

        return AppInfo(
            versionName = packageInfo.versionName ?: ""
        )
    }
}