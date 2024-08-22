package com.mostafatamer.postlibrary.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mostafatamer.postlibrary.MainActivity
import com.mostafatamer.postlibrary.R
import com.mostafatamer.postlibrary.domain.model.Comment
import com.mostafatamer.postlibrary.domain.model.CommentsList
import com.mostafatamer.postlibrary.domain.state.DataState
import com.mostafatamer.postlibrary.presentation.components.CenterContentInLazyItem
import com.mostafatamer.postlibrary.presentation.components.ErrorState
import com.mostafatamer.postlibrary.presentation.components.TopAppBar
import com.mostafatamer.postlibrary.presentation.view_model.PostDetailsViewModel
import com.mostafatamer.postlibrary.ui.theme.Favorite


@Composable
fun PostDetailsScreen(navController: NavHostController, viewModel: PostDetailsViewModel) {

    val mainActivity = LocalContext.current as MainActivity

    LaunchedEffect(viewModel.postId, mainActivity.isConnected) {
        viewModel.loadCommentsConsideringNetwork()

        viewModel.getPost()
        viewModel.checkIfFavoritePost()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Post ${viewModel.postId} Details",
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.manageFavoritePost()
                        }
                    ) {
                        val isFavorite by viewModel.isFavorite.collectAsState()
                        Icon(
                            painter = painterResource(id = R.drawable.favorite),
                            contentDescription = null,
                            tint = if (isFavorite) Favorite else Color.Unspecified
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            Header(viewModel)
            Comments(viewModel, navController)
        }
    }
}


@Composable
private fun Header(viewModel: PostDetailsViewModel) {
    Column(modifier = Modifier.padding(8.dp)) {
        Post(viewModel)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Comments", fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun Post(viewModel: PostDetailsViewModel) {
    Column {
        AnimatedVisibility(visible = viewModel.post != null) {
            val post = viewModel.post!!
            Column {
                Text(
                    text = post.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = post.body.replace("\n", " "))
            }
        }
    }
}

@Composable
private fun Comments(
    viewModel: PostDetailsViewModel,
    navController: NavHostController,
) {
    val postsState by viewModel.comments.collectAsState()
    Card(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) {
        LazyColumn(Modifier.fillMaxSize()) {
            when (postsState) {
                is DataState.Success -> {
                    val posts = (postsState as DataState.Success).data

                    items(posts) { post ->
                        CommentCard(post, posts)
                    }
                }

                is DataState.Loading -> item {
                    CenterContentInLazyItem { CircularProgressIndicator() }
                }

                is DataState.Empty -> item {
                    CenterContentInLazyItem { Text(text = "No comments found") }
                }

                else -> item {
                    CenterContentInLazyItem { ErrorState(viewModel) }
                }
            }
        }
    }
}

@Composable
private fun CommentCard(comment: Comment, commentList: CommentsList) {

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = comment.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = comment.email, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = comment.body.replace("\n", " "))
    }

    if (commentList.indexOf(comment) != commentList.lastIndex) {
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

