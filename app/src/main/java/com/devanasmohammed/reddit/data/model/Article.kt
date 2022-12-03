package com.devanasmohammed.reddit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Article(
    @PrimaryKey(autoGenerate = false)
    val id : String  ,
    val title : String? = null,
    val content:String?= null,
    val author : String? = null,
    val publishedSince: String?= null,
    val sourceUrl : String? = null,
    val thumbnailUrl :String? = null
) : Serializable
