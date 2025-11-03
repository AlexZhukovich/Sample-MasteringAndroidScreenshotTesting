package com.alexzh.designsystem.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alexzh.designsystem.R
import com.alexzh.designsystem.component.button.IconButton
import com.alexzh.designsystem.core.theme.AppTheme

@Composable
fun Section(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1.0f),
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            actions()
        }
        content()
    }
}

@Composable
fun CardSection(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    capitalizeTitle: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1.0f),
                    text = if (capitalizeTitle) title.uppercase() else title,
                    style = MaterialTheme.typography.bodyLarge
                        .copy(
                            color = titleColor,
                            fontWeight = FontWeight.Bold
                        )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            content()
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview_Section_WithoutActions() {
    AppTheme {
        Section(
            title = "Section Title"
        ) {
            Text("Content...")
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview_Section_WithActions() {
    AppTheme {
        Section(
            title = "Section Title",
            actions = {
                IconButton(
                    onClick = {},
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "Edit"
                )
            }
        ) {
            Text("Content...")
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview_CardSection() {
    AppTheme {
        CardSection(
            title = "Card Title"
        ) {
            Text("Content...")
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview_CardSection_CapitalizedTitle() {
    AppTheme {
        CardSection(
            title = "Card Title",
            capitalizeTitle = true
        ) {
            Text("Content...")
        }
    }
}