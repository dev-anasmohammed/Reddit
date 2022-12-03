package com.devanasmohammed.reddit.presentation.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devanasmohammed.reddit.data.model.Article
import com.devanasmohammed.reddit.data.model.Result
import com.devanasmohammed.reddit.data.remote.repositories.ArticlesRepo
import com.devanasmohammed.reddit.util.HandleJsonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class ArticlesViewModel(private val articlesRepo: ArticlesRepo) : ViewModel() {

    private val tag = "ArticlesViewModel"

    private val _articles = MutableLiveData<Result<List<Article>>>()
    val articles: LiveData<Result<List<Article>>> get() = _articles

    init {
        getRemoteArticles()
    }

    private fun getRemoteArticles() {
        viewModelScope.launch {
            _articles.postValue(Result.Loading())
            val response = articlesRepo.getRemoteArticles()
            if (response != null) {
                _articles.postValue(handleGetArticlesResponse(response))
            } else {
                _articles.postValue(Result.Error("Failed to get Articles"))
            }
        }
    }

    private fun handleGetArticlesResponse(response: Response<String>): Result<List<Article>> {
        if (response.isSuccessful) {
            try {
                val listOfArticles = HandleJsonResponse().parseArticlesFromJson(
                    JSONObject(response.body().toString())
                )
                if (listOfArticles != null) {
                    viewModelScope.launch {
                        saveArticlesLocally(listOfArticles.toList())
                    }
                    return Result.Success(listOfArticles.toList())
                } else {
                    return Result.Error("Failed to get Articles")
                }
            } catch (e: Exception) {
                Log.e(tag, "Catch error in handleGetArticlesResponse: ${e.message}")
                return Result.Error("Failed to get Articles")
            }
        }
        return Result.Error("Failed to get Articles")
    }

    private suspend fun saveArticlesLocally(listOfArticles: List<Article>) {
        articlesRepo.deleteArticles()
        articlesRepo.saveArticles(listOfArticles.toList())
    }

    fun getLocalArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _articles.postValue(Result.Loading())
            val response = articlesRepo.getLocalArticles()
            if (response != null) {
                _articles.postValue(Result.Success(response))
            } else {
                _articles.postValue(Result.Error("No articles to load"))
            }
        }
    }

}