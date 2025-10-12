package com.alexzh.moodtracker.ui.feature.actioncategories.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexzh.moodtracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCategoriesScreenTopAppBar(
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.actionCategoriesScreen_title)
            )
        },
        navigationIcon = {
            IconButton(onNavigateUp) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.common_navigateUp_contentDescription)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCategoryDetailsTopAppBar(
    title: String,
    onNavigateUp: (() -> Unit)?,
    showBackButton: Boolean
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (showBackButton && onNavigateUp != null) {
                IconButton(onNavigateUp) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = stringResource(R.string.common_navigateUp_contentDescription)
                    )
                }
            }
        }
    )
}