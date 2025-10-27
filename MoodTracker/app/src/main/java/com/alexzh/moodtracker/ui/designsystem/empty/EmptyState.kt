package com.alexzh.moodtracker.ui.designsystem.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.ui.theme.AppTheme

@Composable
fun BoxScope.EmptyState(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    title: String = stringResource(R.string.common_emptyState_title),
    text: String
) {
    Column(
        modifier = modifier.align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        icon?.let { icon() }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_EmptyState_WithIcon() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyState(
                icon = {
                    Icon(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(R.drawable.ic_monitoring),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                title = "No moods entries found",
                text = "Start tracking your mood by adding your first entry"
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview_EmptyState_WithoutIcon() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyState(
                icon = null,
                title = "No data for this month",
                text = "Switch to a different month or start tracking your mood"
            )
        }
    }
}