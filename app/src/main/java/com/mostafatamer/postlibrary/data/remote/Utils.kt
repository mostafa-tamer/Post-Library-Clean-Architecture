package com.mostafatamer.postlibrary.data.remote

import com.mostafatamer.postlibrary.domain.state.DataState
import retrofit2.Response
import java.net.InetSocketAddress
import java.net.Socket

fun hasInternetAccess(): Boolean {
    return try {
        val socket = Socket()
        socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)  // Google's DNS
        socket.close()
        true
    } catch (e: Exception) {
        false
    }
}


fun <T, R> handleApiResponse(
    response: Response<T>,
    transform: (T) -> R,
): DataState<R> {
    return if (response.isSuccessful) {
        val body = response.body() ?: return DataState.Empty

        return if (body is List<*> && body.isEmpty()) DataState.Empty
        else DataState.Success(transform(body))
    } else {
        DataState.Error(Throwable(DataState.Error.unSuccessfulResponseErrorMessage(response.code())))
    }
}