package com.devanasmohammed.reddit.data.remote.repositories

import android.util.Log
import com.devanasmohammed.reddit.data.remote.api.RetrofitInstance
import retrofit2.Response

class ArticlesRepo {

    private val tag = "ArticlesRepo"

    suspend fun getRemoteArticles() : Response<String>?{
        try {
            return RetrofitInstance.api.getArticles()
        }catch (e:Exception){
            Log.e(tag,"Catch error in getRemoteArticles because of: ${e.message}")
        }
        return null
    }
}