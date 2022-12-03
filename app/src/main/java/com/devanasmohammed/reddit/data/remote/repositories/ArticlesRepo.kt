package com.devanasmohammed.reddit.data.remote.repositories

import android.util.Log
import com.devanasmohammed.reddit.data.local.LocalDatabase
import com.devanasmohammed.reddit.data.model.Article
import com.devanasmohammed.reddit.data.remote.api.RetrofitInstance
import retrofit2.Response

class ArticlesRepo(private val localDatabase: LocalDatabase) {

    private val tag = "ArticlesRepo"

    suspend fun getRemoteArticles(): Response<String>? {
        try {
            return RetrofitInstance.api.getArticles()
        } catch (e: Exception) {
            Log.e(tag, "Catch error in getRemoteArticles because of: ${e.message}")
        }
        return null
    }

    fun getLocalArticles(): List<Article>? {
        try {
            return localDatabase.articleDao().getAllArticles()
        } catch (e: Exception) {
            Log.e(tag, "Catch error in getLocalArticles : ${e.message}")
        }
        return null
    }

    suspend fun saveArticles(list:List<Article>){
        try {
            localDatabase.articleDao().insertArticles(list)
        } catch (e: Exception) {
            Log.e(tag, "Catch error in getLocalArticles : ${e.message}")
        }
    }

    suspend fun deleteArticles(){
        try {
            localDatabase.articleDao().deleteAllArticle()
        } catch (e: Exception) {
            Log.e(tag, "Catch error in deleteArticles : ${e.message}")
        }
    }
}