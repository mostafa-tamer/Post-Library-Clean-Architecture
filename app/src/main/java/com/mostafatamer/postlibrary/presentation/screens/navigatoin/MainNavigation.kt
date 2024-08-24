package com.mostafatamer.postlibrary.presentation.screens.navigatoin

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mostafatamer.postlibrary.activity.view_model.MainActivityViewModel
import com.mostafatamer.postlibrary.presentation.screens.PostDetailsScreen
import com.mostafatamer.postlibrary.presentation.view_model.PostDetailsViewModel
import kotlinx.serialization.Serializable

@Composable
fun MainNavigation(mainNavController: NavHostController, activityViewModel: MainActivityViewModel) {

    val isConnected = activityViewModel.isConnected.collectAsState()

    NavHost(
        navController = mainNavController,
        startDestination = HomeDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<HomeDestination> {
            HomeNavigation(mainNavController, isConnected)
        }

        composable<PostDetailsScreen> {
            val arguments = it.toRoute<PostDetailsScreen>()

            val viewModel = hiltViewModel<PostDetailsViewModel>()
            viewModel.init(arguments.postId)

            PostDetailsScreen(mainNavController, viewModel, isConnected)
        }
    }
}

@Serializable
object HomeDestination

@Serializable
data class PostDetailsScreen(val postId: Int)
