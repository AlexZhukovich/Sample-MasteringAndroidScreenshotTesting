package com.alexzh.moodtracker.ui.feature.settings.licence

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
            SettingLicensesScreenTopAppBar(
                onNavigateUp = onNavigateUp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingLicensesScreenTopAppBar(
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.settingsScreen_thirdPartyLicenses_title))
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.common_navigateUp_contentDescription)
                )
            }
        }
    )
}