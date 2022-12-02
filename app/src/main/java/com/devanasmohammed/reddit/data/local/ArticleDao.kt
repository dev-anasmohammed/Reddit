package com.devanasmohammed.reddit.data.local

import androidx.room.*
import com.devanasmohammed.reddit.data.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertArticles(articles: List<Article>)

    @Query("SELECT * FROM article")
    fun getAllArticles() : List<Article>

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("DELETE FROM article")
    suspend fun deleteAllArticle()
}