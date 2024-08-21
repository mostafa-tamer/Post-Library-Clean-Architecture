package com.mostafatamer.postlibrary.presentation.screens.navigatoin

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mostafatamer.postlibrary.presentation.screens.PostDetailsScreen
import com.mostafatamer.postlibrary.presentation.screens.PostScreen
import com.mostafatamer.postlibrary.presentation.view_model.PostDetailsViewModel
import com.mostafatamer.postlibrary.presentation.view_model.PostViewModel
import kotlinx.serialization.Serializable

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = PostScreen) {
        composable<PostScreen> {
            val viewModel = hiltViewModel<PostViewModel>()
            PostScreen(navController, viewModel)
        }

        composable<PostDetailsScreen> {
            val arguments = it.toRoute<PostDetailsScreen>()

            val viewModel = hiltViewModel<PostDetailsViewModel>()
            viewModel.init(arguments.postId)

            PostDetailsScreen(navController, viewModel)
        }
    }
}

@Serializable
object PostScreen

@Serializable
data class PostDetailsScreen(val postId: Int)