package com.mostafatamer.postlibrary.presentation.view_model

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

    private val _favoritePosts = MutableStateFlow<DataState<PostList>>(DataState.Loading)
    val favoritePosts: StateFlow<DataState<PostList>> get() = _favoritePosts

    fun getFavoritePosts() {
        viewModelScope.launch {
            _favoritePosts.value = postUseCase.getFavoritePosts()
        }
    }
}