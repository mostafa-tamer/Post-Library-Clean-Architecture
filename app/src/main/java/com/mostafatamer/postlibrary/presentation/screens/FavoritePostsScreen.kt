package com.mostafatamer.postlibrary.presentation.screens

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
import androidx.compose.ui.unit.dp
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.state.DataState
import com.mostafatamer.postlibrary.presentation.components.CenterContentInLazyItem
import com.mostafatamer.postlibrary.presentation.components.TopAppBar
import com.mostafatamer.postlibrary.presentation.shared_components.PostCardContent
import com.mostafatamer.postlibrary.presentation.view_model.FavoritePostViewModel


@Composable
fun FavoritePostsScreen(viewModel: FavoritePostViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getFavoritePosts()
    }

    Scaffold(
        topBar = { TopAppBar(title = "Favorite Posts") },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .padding(horizontal = 8.dp),
        ) {
            val favoritePosts by viewModel.favoritePosts.collectAsState()

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                when (favoritePosts) {
                    is DataState.Success -> {
                        val posts = (favoritePosts as DataState.Success).data
                        items(posts) { post ->
                            FavoritePostCard(post)
                        }
                    }

                    is DataState.Loading -> {
                        item {
                            CenterContentInLazyItem { CircularProgressIndicator() }
                        }
                    }

                    is DataState.Empty -> {
                        item {
                            CenterContentInLazyItem { Text(text = "No favorite posts found") }
                        }
                    }

                    else -> {
                        item {
                            CenterContentInLazyItem { Text(text = "Something went wrong") }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun FavoritePostCard(post: Post) {
    Card {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            PostCardContent(post)
        }
    }
}