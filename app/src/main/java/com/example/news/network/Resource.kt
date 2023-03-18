package com.example.news.network

import com.example.news.data.models.response.errorresponse.ErrorResponse

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()

    data class Error(
        val errorResponse: ErrorResponse,
        val errorMsg: String = errorResponse.message
    ) : Resource<Nothing>() {
    }

    data class UnknownError(
        val exception: java.lang.Exception?,
        val errorMsg: String = exception?.message ?: "Something went wrong"
    ) : Resource<Nothing>()
}

