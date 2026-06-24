package com.alexzh.designsystem.component.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)