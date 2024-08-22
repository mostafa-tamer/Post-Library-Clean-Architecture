package com.mostafatamer.postlibrary

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
    var isConnected by mutableStateOf<Boolean?>(null)

    private val connectivityManager: ConnectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnected = true
            }

            override fun onLost(network: Network) {
                isConnected = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        checkConnection()

        setContent {
            ConnectionStatusToast()

            PostLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainNavigation(mainNavController = rememberNavController())
                    }
                }
            }
        }
    }

    @Composable
    private fun ConnectionStatusToast() {
        var alreadyConnected by remember { mutableStateOf(true) }

        if (isConnected == true && !alreadyConnected) {
            Toast.makeText(this, "Back Online", Toast.LENGTH_SHORT).show()
        } else if (isConnected == null || isConnected != true) {
            alreadyConnected = false
            Toast.makeText(this, "Connection Lost", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkConnection() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onDestroy() {
        super.onDestroy()

        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
