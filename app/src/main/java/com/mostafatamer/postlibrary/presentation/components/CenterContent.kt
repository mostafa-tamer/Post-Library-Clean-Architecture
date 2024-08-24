package com.mostafatamer.postlibrary.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun LazyItemScope.CenterContentInLazyItem(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier.fillParentMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
