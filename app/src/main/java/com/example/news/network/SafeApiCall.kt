package com.example.news.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.Reader
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
                    Resource.Failure(
                        errorMsg = response.errorBody()?.charStream()?.use(Reader::readText)
                            ?: "Something went wrong",
                        exception = null
                    )
                }
            } catch (e: java.lang.Exception) {
                Resource.Failure(e.message ?: "Something went wrong", e)
            }
        }
    }
}