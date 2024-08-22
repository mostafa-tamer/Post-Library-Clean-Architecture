package com.mostafatamer.postlibrary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.NavigatorItem(
    text: String,
    enabled: Boolean,
) {
    Box(modifier = Modifier.Companion.weight(1f)) {
        Column {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        if (enabled) {
                            MaterialTheme.colorScheme.primary
                        } else Color.Unspecified
                    )
            )

        }
    }
}