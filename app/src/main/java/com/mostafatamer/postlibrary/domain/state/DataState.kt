package com.mostafatamer.postlibrary.domain.state

sealed class DataState<out T> {
    data object Loading : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Throwable) : DataState<Nothing>()
    data object Empty : DataState<Nothing>()
}
