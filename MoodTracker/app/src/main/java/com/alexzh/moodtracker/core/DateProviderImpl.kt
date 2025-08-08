package com.alexzh.moodtracker.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.core.content.ContextCompat
import com.alexzh.moodtracker.domain.provider.DateProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class DateProviderImpl(private val context: Context) : DateProvider {
    private val deviceTimeZone: ZoneId
        get() = ZoneId.systemDefault()

    override fun getCurrentDate(): LocalDate =
        ZonedDateTime.now(deviceTimeZone).toLocalDate()

    override val currentDateFlow: Flow<LocalDate> = callbackFlow {
        var currentDate = getCurrentDate()
        var currentTimeZone = deviceTimeZone

        trySend(currentDate)
        val dateTimeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_TIME_TICK,
                    Intent.ACTION_TIME_CHANGED,
                    Intent.ACTION_DATE_CHANGED,
                    Intent.ACTION_TIMEZONE_CHANGED -> {
                        val newDate = getCurrentDate()
                        val newTimeZone = deviceTimeZone
                        if (newDate != currentDate || newTimeZone != currentTimeZone) {
                            currentDate = newDate
                            currentTimeZone = newTimeZone
                            trySend(currentDate)
                        }
                    }
                }
            }
        }

        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_DATE_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(dateTimeReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            ContextCompat.registerReceiver(context, dateTimeReceiver, intentFilter, ContextCompat.RECEIVER_NOT_EXPORTED)
        }

        awaitClose {
            context.unregisterReceiver(dateTimeReceiver)
        }
    }
}