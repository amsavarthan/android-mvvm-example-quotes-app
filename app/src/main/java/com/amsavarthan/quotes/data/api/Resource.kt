package com.amsavarthan.quotes.data.api

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String? = "Some error occurred") : Resource<T>(message = message)
    class Loading<T>() : Resource<T>()
}
