package com.b18dccn562.finalproject.common

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val exception: Exception? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(
        message: String? = null,
        data: T? = null,
        exception: Exception? = null,
        errorCode: Int? = null
    ) :
        Resource<T>(data, message, exception, errorCode)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}