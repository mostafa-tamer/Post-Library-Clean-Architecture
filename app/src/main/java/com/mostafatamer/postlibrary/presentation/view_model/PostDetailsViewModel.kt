package com.mostafatamer.postlibrary.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafatamer.postlibrary.domain.model.CommentsList
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.state.DataState
import com.mostafatamer.postlibrary.domain.use_case.MockServerUseCase
import com.mostafatamer.postlibrary.domain.use_case.PostUseCase
import com.mostafatamer.postlibrary.presentation.view_model.abstraction.PreSaveable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
    private val mockServerUseCase: MockServerUseCase
) : ViewModel(), PreSaveable {

    private val _comments = MutableStateFlow<DataState<CommentsList>>(DataState.Loading)
    val comments: StateFlow<DataState<CommentsList>> get() = _comments

    private var _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite

    private var _post = MutableStateFlow<DataState<Post>>(DataState.Loading)
    val post get() = _post

    var postId: Int = -1

    fun init(postId: Int) {
        if (postId < 1)
            throw IllegalArgumentException("postId must be greater than 0")

        this.postId = postId
    }

    fun loadComments() {
        viewModelScope.launch {
            val result = postUseCase.getComments(postId)
            loadDataCondition(result, _comments)
        }
    }

    fun checkIfFavoritePost() {
        viewModelScope.launch {
            onPostRetrieved(
                onError = { _isFavorite.value = false }
            ) { post ->
                val isFavoritePost = mockServerUseCase.isFavoritePost(post)
                _isFavorite.value = isFavoritePost
            }
        }
    }

    private suspend fun onPostRetrieved(
        onError: () -> Unit = {},
        onRetrieved: suspend (Post) -> Unit,
    ) {
        post.collect { postDataState ->
            if (postDataState is DataState.Success) {
                val post = postDataState.data
                onRetrieved(post)
            } else {
                onError()
            }
        }
    }

    override fun getPreSavedData() {
        viewModelScope.launch {
            _comments.value = postUseCase.getSavedComments(postId)
        }
    }

    private fun addPostToFavorite() {
        viewModelScope.launch {
            onPostRetrieved {
                mockServerUseCase.addToFavoritePost(it)
                _isFavorite.value = true
            }
        }
    }

    private fun removePostFromFavorite() {
        viewModelScope.launch {
            onPostRetrieved {
                val result = mockServerUseCase.removeFromFavoritePost(it)
                if (result is DataState.Success) {
                    _isFavorite.value = false
                }
            }
        }
    }

    fun manageFavoritePost() {
        if (_isFavorite.value) {
            removePostFromFavorite()
        } else {
            addPostToFavorite()
        }
    }

    fun getPost() {
        viewModelScope.launch {
            _post.value = postUseCase.getPostById(postId)
        }
    }
}