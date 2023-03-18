package com.example.news.network

import com.example.news.data.models.response.errorresponse.ErrorResponse

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()

    sealed class Failure(val errorMsg: String) : Resource<Nothing>() {
        data class Error(
            val errorResponse: ErrorResponse
        ) : Failure(errorResponse.message)

        data class UnknownError(
            val exception: java.lang.Exception?
        ) : Failure(exception?.message ?: "Something went wrong")
    }

}

