package com.alexzh.designsystem.component.selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.R
import com.alexzh.designsystem.component.button.IconButton
import com.alexzh.designsystem.core.theme.AppTheme

@Composable
fun PeriodSelector(
    modifier: Modifier = Modifier,
    label: String,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    previousIcon: Painter = painterResource(R.drawable.ic_keyboard_arrow_left),
    previousEnabled: Boolean = true,
    nextIcon: Painter = painterResource(R.drawable.ic_keyboard_arrow_right),
    nextEnabled: Boolean = true,
    previousContentDescription: String? = null,
    nextEnabledContentDescription: String? = null,
    nextDisabledContentDescription: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        IconButton(
            modifier = if (previousContentDescription != null) {
                Modifier.semantics {
                    contentDescription = previousContentDescription
                }
            } else {
                Modifier
            },
            onClick = onPrevious,
            painter = previousIcon,
            contentDescription = previousContentDescription,
            enabled = previousEnabled
        )

        Text(
            text = label,
            style = textStyle,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            modifier = when {
                nextEnabledContentDescription != null && nextDisabledContentDescription != null -> {
                    Modifier.semantics {
                        contentDescription = if (nextEnabled) {
                            nextEnabledContentDescription
                        } else {
                            nextDisabledContentDescription
                        }
                    }
                }
                nextEnabledContentDescription != null -> {
                    Modifier.semantics {
                        contentDescription = nextEnabledContentDescription
                    }
                }
                else -> Modifier
            },
            onClick = onNext,
            painter = nextIcon,
            contentDescription = when {
                nextEnabled && nextEnabledContentDescription != null -> nextEnabledContentDescription
                !nextEnabled && nextDisabledContentDescription != null -> nextDisabledContentDescription
                nextEnabledContentDescription != null -> nextEnabledContentDescription
                else -> null
            },
            enabled = nextEnabled,
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_PeriodSelector_PreviousAndNextEnabled() {
    AppTheme {
        PeriodSelector(
            label = "January 2025",
            onPrevious = {},
            onNext = {},
            previousEnabled = true,
            nextEnabled = true
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_PeriodSelector_PreviousAndNextDisabled() {
    AppTheme {
        PeriodSelector(
            label = "Current Month",
            onPrevious = {},
            onNext = {},
            previousEnabled = false,
            nextEnabled = false
        )
    }
}