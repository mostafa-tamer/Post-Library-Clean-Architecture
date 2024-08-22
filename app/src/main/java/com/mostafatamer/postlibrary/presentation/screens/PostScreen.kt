package com.mostafatamer.postlibrary.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mostafatamer.postlibrary.MainActivity
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.state.DataState
import com.mostafatamer.postlibrary.presentation.components.CenterContentInLazyItem
import com.mostafatamer.postlibrary.presentation.components.ErrorState
import com.mostafatamer.postlibrary.presentation.components.TopAppBar
import com.mostafatamer.postlibrary.presentation.screens.navigatoin.PostDetailsScreen
import com.mostafatamer.postlibrary.presentation.shared_components.PostCardContent
import com.mostafatamer.postlibrary.presentation.view_model.PostViewModel

@Composable
fun PostScreen(navHostController: NavHostController, viewModel: PostViewModel) {

    val mainActivity = LocalContext.current as MainActivity

    LaunchedEffect(mainActivity.isConnected) {
        viewModel.loadPostsConsideringNetwork()
    }

    Scaffold(
        topBar = { TopAppBar(title = "Posts") },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .padding(horizontal = 8.dp),
        ) {
            Posts(viewModel, navHostController)
        }
    }
}


@Composable
private fun Posts(
    viewModel: PostViewModel,
    navHostController: NavHostController,
) {
    val postsState by viewModel.posts.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {

        when (postsState) {
            is DataState.Success -> {
                val posts = (postsState as DataState.Success).data

                items(posts) { post ->
                    PostCard(post, navHostController)
                }
            }

            is DataState.Loading -> item {
                CenterContentInLazyItem { CircularProgressIndicator() }
            }

            is DataState.Empty -> item {
                CenterContentInLazyItem { Text(text = "No posts found") }
            }

            else -> item {
                CenterContentInLazyItem { ErrorState(viewModel) }
            }
        }
    }
}


@Composable
private fun PostCard(post: Post, navHostController: NavHostController) {
    Card {
        Column(
            modifier = Modifier
                .clickable {
                    navHostController.navigate(
                        PostDetailsScreen(postId = post.id)
                    )
                }
                .padding(8.dp)
        ) {
            PostCardContent(post)
        }
    }
}

