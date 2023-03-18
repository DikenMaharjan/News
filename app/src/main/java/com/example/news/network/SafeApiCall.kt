package com.example.news.network

import com.example.news.data.models.response.errorresponse.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SafeApiCall @Inject constructor() {
    suspend fun <T> execute(operation: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = operation.invoke()
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Failure.Error(
                        errorResponse = Gson().fromJson(
                            response.errorBody()?.charStream(), ErrorResponse::class.java
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                Resource.Failure.UnknownError(
                    exception = e
                )
            }
        }
    }
}