package com.alexzh.moodtracker.widget.ui.configuration

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alexzh.designsystem.core.theme.AppTheme
import com.alexzh.moodtracker.widget.ui.MoodTrackerWidgetReceiver
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class WidgetConfigurationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setResult(RESULT_CANCELED)

        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        val onConfigurationComplete: (Boolean) -> Unit = { success ->
            if (success) {
                sendWidgetUpdateBroadcast(appWidgetId)
                val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                setResult(RESULT_OK, resultValue)
            }
            finish()
        }

        val viewModel: WidgetConfigurationViewModel = getViewModel {
            parametersOf(appWidgetId, onConfigurationComplete)
        }

        setContent {
            AppTheme {
                WidgetConfigurationScreen(viewModel = viewModel)
            }
        }
    }

    private fun sendWidgetUpdateBroadcast(appWidgetId: Int) {
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE).apply {
            component = ComponentName(this@WidgetConfigurationActivity, MoodTrackerWidgetReceiver::class.java)
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        }
        sendBroadcast(intent)
    }
}