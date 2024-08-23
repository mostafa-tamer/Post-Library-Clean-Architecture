package com.mostafatamer.postlibrary.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafatamer.postlibrary.domain.model.PostList
import com.mostafatamer.postlibrary.domain.state.DataState
import com.mostafatamer.postlibrary.domain.use_case.PostUseCase
import com.mostafatamer.postlibrary.loadDataCondition
import com.mostafatamer.postlibrary.presentation.view_model.abstraction.PreSaveable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
) : ViewModel(), PreSaveable {

    private val _posts = MutableStateFlow<DataState<PostList>>(DataState.Loading)
    val posts: StateFlow<DataState<PostList>> get() = _posts

    fun loadPosts() {
        viewModelScope.launch {
            val result = postUseCase.getPosts()
            loadDataCondition(result, _posts)
        }
    }


    override fun getPreSavedData() {
        viewModelScope.launch {
            _posts.value = postUseCase.getSavedPosts()
        }
    }
}