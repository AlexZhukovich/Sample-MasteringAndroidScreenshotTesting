package com.alexzh.moodtracker.ui.designsystem.selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R

@Composable
fun PeriodSelector(
    modifier: Modifier = Modifier,
    label: String,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    previousEnabled: Boolean = true,
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
            onClick = onPrevious,
            enabled = previousEnabled,
            modifier = if (previousContentDescription != null) {
                Modifier.semantics {
                    contentDescription = previousContentDescription
                }
            } else {
                Modifier
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_keyboard_arrow_left),
                contentDescription = previousContentDescription
            )
        }

        Text(
            text = label,
            style = textStyle,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            onClick = onNext,
            enabled = nextEnabled,
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
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_keyboard_arrow_right),
                contentDescription = when {
                    nextEnabled && nextEnabledContentDescription != null -> nextEnabledContentDescription
                    !nextEnabled && nextDisabledContentDescription != null -> nextDisabledContentDescription
                    nextEnabledContentDescription != null -> nextEnabledContentDescription
                    else -> null
                }
            )
        }
    }
}