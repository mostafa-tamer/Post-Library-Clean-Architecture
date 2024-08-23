package com.mostafatamer.postlibrary

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mostafatamer.postlibrary.presentation.screens.navigatoin.MainNavigation
import com.mostafatamer.postlibrary.ui.theme.PostLibraryTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isConnected by viewModel.isConnected.collectAsState()

            ConnectionStatusToast(isConnected)

            if (isConnected == true) {
                LaunchedEffect(isConnected) {
                    viewModel.syncFavoritePosts()
                }
            }

            PostLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val navController = rememberNavController()
                        MainNavigation(mainNavController =navController, viewModel)
                    }
                }
            }
        }
    }

    @Composable
    private fun ConnectionStatusToast(isConnected: Boolean?) {
        var alreadyConnected by remember { mutableStateOf(true) }

        if (isConnected == true && !alreadyConnected) {
            Toast.makeText(this, "Back Online", Toast.LENGTH_SHORT).show()
        } else if (isConnected == null || isConnected != true) {
            alreadyConnected = false
            Toast.makeText(this, "Connection Lost", Toast.LENGTH_SHORT).show()
        }
    }
}

