package com.devanasmohammed.reddit.data.model

data class Article(
    val id : Int? = null ,
    val title : String? = null,
    val content:String?= null,
    val author : String? = null,
    val publishedSince: String?= null,
    val sourceUrl : String? = null,
    val thumbnailUrl :String? = null
)
