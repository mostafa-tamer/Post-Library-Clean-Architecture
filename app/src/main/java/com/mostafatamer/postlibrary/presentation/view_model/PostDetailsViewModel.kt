package com.mostafatamer.postlibrary.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafatamer.postlibrary.domain.model.CommentsList
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.state.DataState
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
) : ViewModel(), PreSaveable {

    private val _comments = MutableStateFlow<DataState<CommentsList>>(DataState.Loading)
    val comments: StateFlow<DataState<CommentsList>> get() = _comments

    private var _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite

    var post by mutableStateOf<Post?>(null)
        private set

    var postId: Int = -1

    fun init(postId: Int) {
        if (postId < 1)
            throw IllegalArgumentException("postId must be greater than 0")

        this.postId = postId
    }

    private fun loadComments() {
        viewModelScope.launch {
            _comments.value = postUseCase.getComments(postId)
        }
    }

    fun loadCommentsConsideringNetwork() {
        if (_comments.value is DataState.Success<CommentsList>) {
            val comments = (_comments.value as DataState.Success).data
            if (comments.isEmpty()) {
                loadComments()
            }
        } else {
            loadComments()
        }
    }

    fun checkIfFavoritePost() {
        viewModelScope.launch {
            val isFavoriteFlow = postUseCase.isFavoritePost(postId)

            isFavoriteFlow.collect { isFavorite ->
                _isFavorite.value = isFavorite
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
            postUseCase.addToFavoritePost(postId)
        }
    }

    private fun removePostFromFavorite() {
        viewModelScope.launch {
            postUseCase.removeFromFavoritePost(postId)
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
            val postState = postUseCase.getPostById(postId)
            post = postState.data
        }
    }
}