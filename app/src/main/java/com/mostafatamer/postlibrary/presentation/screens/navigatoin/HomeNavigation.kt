package com.mostafatamer.postlibrary.presentation.screens.navigatoin


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mostafatamer.postlibrary.presentation.components.BottomNavigationBar
import com.mostafatamer.postlibrary.presentation.screens.FavoritePostsScreen
import com.mostafatamer.postlibrary.presentation.screens.PostScreen
import com.mostafatamer.postlibrary.presentation.view_model.FavoritePostViewModel
import com.mostafatamer.postlibrary.presentation.view_model.PostViewModel
import kotlinx.serialization.Serializable

@Composable
fun HomeNavigation(mainNavController: NavHostController) {
    val nestedNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController = nestedNavController)
        }
    ) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            NavHost(
                navController = nestedNavController, startDestination = PostDestination,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }
            ) {
                composable<PostDestination> {
                    val viewModel = hiltViewModel<PostViewModel>()
                    PostScreen(mainNavController, viewModel)
                }

                composable<FavoritePostsDestination> {
                    val viewModel = hiltViewModel<FavoritePostViewModel>()
                    FavoritePostsScreen(viewModel)
                }
            }
        }
    }
}

@Serializable
object PostDestination

@Serializable
object FavoritePostsDestination