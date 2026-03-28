package com.alexzh.designsystem.core.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import java.util.Locale

val currentLocale: Locale
    @Composable
    @ReadOnlyComposable
    get() = (ConfigurationCompat.getLocales(LocalConfiguration.current)[0]
        ?: Locale.getDefault()).withNativeDigits()

/**
 * Ensures that Arabic locales explicitly use the proper number system.
 */
fun Locale.withNativeDigits(): Locale {
    return if (language == "ar" && getUnicodeLocaleType("nu") == null) {
        Locale.Builder().setLocale(this).setUnicodeLocaleKeyword("nu", "arab").build()
    } else {
        this
    }
}