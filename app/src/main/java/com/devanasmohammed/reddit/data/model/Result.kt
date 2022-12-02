package com.devanasmohammed.reddit.data.model


sealed class Result<T>(
    val data : T? = null,
    val message : String ? = null
){
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(errorMessage: String? , data: T? = null ) : Result<T>(data , errorMessage)
    class Loading<T> : Result<T>()
}
