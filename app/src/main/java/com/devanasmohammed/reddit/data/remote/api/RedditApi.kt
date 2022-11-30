package com.devanasmohammed.reddit.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RedditApi {
    @GET("r/kotlin/.json")
    @Headers("Accept: application/json")
    suspend fun getArticles() : Response<String>
}