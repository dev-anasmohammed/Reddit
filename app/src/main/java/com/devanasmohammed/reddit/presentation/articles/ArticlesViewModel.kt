package com.devanasmohammed.reddit.presentation.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devanasmohammed.reddit.data.model.Article
import com.devanasmohammed.reddit.data.model.Result
import com.devanasmohammed.reddit.util.HandleJsonResponse
import com.devanasmohammed.reddit.data.remote.repositories.ArticlesRepo
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class ArticlesViewModel(private val articlesRepo: ArticlesRepo) : ViewModel() {

    private val _articles = MutableLiveData<Result<List<Article>>>()
    val article: LiveData<Result<List<Article>>> get() = _articles

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
            val listOfArticles =
                HandleJsonResponse().parseArticleFromJson(JSONObject(response.body().toString()))
            return if(listOfArticles!=null){
                Result.Success(listOfArticles)
            }else{
                Result.Error("Failed to get Articles")
            }
        }
        return Result.Error("Failed to get Articles")
    }
}