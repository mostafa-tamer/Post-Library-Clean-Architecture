package com.mostafatamer.postlibrary.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.mostafatamer.postlibrary.presentation.view_model.abstraction.PreSaveable

@Composable
fun ErrorState(viewModel: PreSaveable) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Error Loading Data")
        Button(
            onClick = {
                viewModel.loadPreSavedData()
            }
        ) {
            Text(text = "Load Pre-Saved Data")
        }
    }
}