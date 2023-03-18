package com.example.news.data.models.response.errorresponse

data class ErrorResponse(
    val code: String,
    val message: String,
    val status: String
)