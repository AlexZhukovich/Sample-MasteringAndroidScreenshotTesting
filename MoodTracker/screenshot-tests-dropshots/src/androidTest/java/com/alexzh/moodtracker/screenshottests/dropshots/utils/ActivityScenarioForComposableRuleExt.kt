package com.alexzh.moodtracker.screenshottests.dropshots.utils

import android.app.Activity
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.test.core.app.ActivityScenario
import sergio.sastre.uitesting.utils.activityscenario.ActivityScenarioForComposableRule

fun ActivityScenarioForComposableRule.setContent(
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    content: @Composable () -> Unit
): ActivityScenario<out Activity> = activityScenario.onActivity { activity ->
    activity.setContentView(
        view = ComposeView(activity).apply {
            setContent { content() }
        },
        params = ViewGroup.LayoutParams(width, height)
    )
}