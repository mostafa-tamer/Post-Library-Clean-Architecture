package com.mostafatamer.postlibrary.presentation.view_model

import com.mostafatamer.postlibrary.domain.state.DataState
import kotlinx.coroutines.flow.MutableStateFlow


fun <T> loadDataCondition(
    newResult: DataState<T>, oldResult: MutableStateFlow<DataState<T>>,
) {
    if (newResult !is DataState.Error || oldResult.value is DataState.Loading) {
        oldResult.value = newResult
    }
}
