package com.skl.newsapp.utils

// A generic class that contains data and status about loading this data.
//Network Responses
sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(error: String, data: T? = null) : Resource<T>(data, error)
}
