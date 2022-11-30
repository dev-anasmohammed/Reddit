package com.devanasmohammed.reddit.util

import android.text.format.DateFormat
import android.util.Log
import com.devanasmohammed.reddit.data.model.Article
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class HandleJsonResponse {

    private val tag = "HandleJsonResponse"

    fun parseArticleFromJson(jsonResponse: JSONObject): List<Article>? {
        try {
            val data = jsonResponse.getJSONObject("data")
            val children = data.getJSONArray("children")
            val articlesList = ArrayList<Article>()
            for (i in 0 until children.length()) {
                //get article
                val article = children.getJSONObject(i)
                //get article all data
                val articleData = article.getJSONObject("data")
                val title = articleData.getString("title")
                val content = articleData.getString("selftext")
                val author = articleData.getString("author")
                val publishedDate = articleData.getLong("created")
                val sourceUrl = articleData.getString("url")
                //get article media
                val media = articleData.getString("secure_media")
                var thumbnailUrl = ""
                if (media != "null") {
                    val secureMedia = articleData.getJSONObject("secure_media")
                    val oembed = secureMedia.getJSONObject("oembed")
                    thumbnailUrl = if (oembed.has("thumbnail_url")) {
                        oembed.getString("thumbnail_url")
                    } else {
                        ""
                    }
                }

                val formattedPublishedSince =
                    getFormattedPublishedSince(formatPublishedSince(publishedDate))

                articlesList.add(
                    Article(
                        0,
                        title,
                        content,
                        author,
                        formattedPublishedSince,
                        sourceUrl,
                        thumbnailUrl
                    )
                )
            }
            return articlesList

        } catch (e: Exception) {
            Log.e(tag, "Catch error in parseArticleFromJson because of: ${e.message}")
        }
        return null
    }


    private fun formatPublishedSince(timeStamp: Long): String {
        val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = timeStamp * 1000
        return DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString()
    }

    //11 hours ago
    private fun getFormattedPublishedSince(differDate: String): String {
        //get current date and time
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault())
        val currentDate = sdf.format(Date())

        //get the difference between two dates
        val date1 = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault())
            .parse(currentDate)
        val date2 = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault())
            .parse(differDate)
        val diff: Long = date1!!.time - date2!!.time

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        //days
        if (days.toInt() == 0) {
            //hours
            if (hours.toInt() == 0) {
                //minutes
                if (minutes.toInt() == 0) {
                    //seconds
                    if (seconds.toInt() == 0) {
                        return "now"
                    } else {
                        return "$seconds seconds ago"
                    }
                } else {
                    return "$minutes minutes ago"
                }
            } else {
                return "$hours hours ago"
            }
        } else {
            return "$days days ago"
        }
    }
}