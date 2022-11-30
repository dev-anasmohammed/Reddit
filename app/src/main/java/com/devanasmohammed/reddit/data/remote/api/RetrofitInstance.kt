package com.devanasmohammed.reddit.data.remote.api

import com.devanasmohammed.reddit.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitInstance {
    companion object {
        //lazy means which means we only init this here once
        private val retrofit by lazy {
            //attached to retrofit object to see which
            //request we are making and what is the response
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build()
        }

        val api: RedditApi by lazy{
            retrofit.create(RedditApi::class.java)
        }
    }
}