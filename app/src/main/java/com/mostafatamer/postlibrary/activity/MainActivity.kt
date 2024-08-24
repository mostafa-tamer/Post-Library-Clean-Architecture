package com.mostafatamer.postlibrary.activity

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mostafatamer.postlibrary.activity.view_model.MainActivityViewModel
import com.mostafatamer.postlibrary.presentation.screens.navigatoin.MainNavigation
import com.mostafatamer.postlibrary.ui.theme.PostLibraryTheme
import com.mostafatamer.postlibrary.worker_manager.SyncManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "Notification Permission Is Required", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        SyncManager(this).scheduleSync(1, TimeUnit.DAYS)

        setContent {
            val isConnected = viewModel.isConnected.collectAsState()

            ConnectionStatusToast(isConnected)

            if (isConnected.value == true) {
                LaunchedEffect(isConnected.value) {
                    viewModel.syncFavoritePosts()
                }
            }

            PostLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val navController = rememberNavController()
                        MainNavigation(mainNavController = navController, viewModel)
                    }
                }
            }
        }
    }

    @Composable
    private fun ConnectionStatusToast(isConnected: State<Boolean?>) {
        var alreadyConnected by remember { mutableStateOf(true) }

        if (isConnected.value == true && !alreadyConnected) {
            Toast.makeText(this, "Back Online", Toast.LENGTH_SHORT).show()
        } else if (isConnected.value == false) {
            alreadyConnected = false
            Toast.makeText(this, "Connection Lost", Toast.LENGTH_SHORT).show()
        }
    }
}

