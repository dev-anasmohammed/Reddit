package com.devanasmohammed.reddit.presentation.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devanasmohammed.reddit.data.remote.repositories.ArticlesRepo

@Suppress("UNCHECKED_CAST")
class ArticlesViewModelFactory(private val articlesRepo: ArticlesRepo)
    : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArticlesViewModel(articlesRepo) as T
    }
}