package com.alexzh.moodtracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.ui.theme.AppTheme
import com.android.tools.screenshot.PreviewTest

@PreviewTest
@Preview(wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE, showBackground = true)
@Preview(wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE, showBackground = true)
@Preview(wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE, showBackground = true)
@Preview(wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE, showBackground = true)
@Composable
fun PrimaryAndSecondaryButtonsPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Button(
                modifier = Modifier.width(200.dp),
                onClick = {}
            ) {
                Text("Primary button")
            }
            OutlinedButton(
                modifier = Modifier.width(200.dp),
                onClick = {}
            ) {
                Text("Secondary button")
            }
        }
    }
}