package com.mostafatamer.postlibrary.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafatamer.postlibrary.domain.model.PostList
import com.mostafatamer.postlibrary.domain.state.DataState
import com.mostafatamer.postlibrary.domain.use_case.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePostViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
) : ViewModel() {

    var isRefreshing by mutableStateOf(false)

    private val _favoritePosts = MutableStateFlow<DataState<PostList>>(DataState.Loading)
    val favoritePosts: StateFlow<DataState<PostList>> get() = _favoritePosts

    fun loadFavoritePosts() {
        viewModelScope.launch {
            val result = postUseCase.loadFavoritePosts()
            loadDataCondition(result, _favoritePosts)
            isRefreshing = false
        }
    }
}