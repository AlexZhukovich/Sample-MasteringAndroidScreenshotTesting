package com.alexzh.moodtracker.screenshottests.dropshots.utils

import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun <T : ComponentActivity> ActivityScenarioRule<T>.setContent(
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    content: @Composable () -> Unit
): ComposeView {
    lateinit var composeView: ComposeView

    scenario.onActivity { activity ->
        composeView = ComposeView(activity).apply {
            setContent(content)
        }
        activity.setContentView(
            view = composeView,
            params = ViewGroup.LayoutParams(width, height)
        )
    }

    return composeView
}