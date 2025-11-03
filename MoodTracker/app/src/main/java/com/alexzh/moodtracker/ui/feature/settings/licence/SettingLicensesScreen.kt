package com.alexzh.moodtracker.ui.feature.settings.licence

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexzh.designsystem.component.bars.TopAppBarWithBackButton
import com.alexzh.moodtracker.R
import com.mikepenz.aboutlibraries.ui.compose.android.rememberLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer

@Composable
fun SettingLicensesScreen(
    onNavigateUp: () -> Unit
) {
    SettingLicensesScreenContent(
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun SettingLicensesScreenContent(
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarWithBackButton(
                title = stringResource(R.string.settingsScreen_thirdPartyLicenses_title),
                onBack = onNavigateUp
            )
        }
    ) { innerPadding ->
        val libraries by rememberLibraries(R.raw.aboutlibraries)
        LibrariesContainer(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding),
            libraries = libraries
        )
    }
}