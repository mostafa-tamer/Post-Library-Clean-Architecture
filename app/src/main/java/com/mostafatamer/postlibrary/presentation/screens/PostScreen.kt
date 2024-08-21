package com.mostafatamer.postlibrary.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.state.DataState
import com.mostafatamer.postlibrary.presentation.components.CenterContentInLazyItem
import com.mostafatamer.postlibrary.presentation.components.ErrorState
import com.mostafatamer.postlibrary.presentation.components.TopAppBar
import com.mostafatamer.postlibrary.presentation.screens.navigatoin.PostDetailsScreen
import com.mostafatamer.postlibrary.presentation.view_model.PostViewModel

@Composable
fun PostScreen(navHostController: NavHostController, viewModel: PostViewModel) {

    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }


    Scaffold(
        topBar = {
            TopAppBar(title = "Posts")
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
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
            Text(text = post.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = post.body.replace("\n", " "))
        }
    }
}