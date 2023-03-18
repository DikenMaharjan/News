package com.example.news.network

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    data class Failure(val errorMsg: String, val exception: java.lang.Exception?) :
        Resource<Nothing>()
}

