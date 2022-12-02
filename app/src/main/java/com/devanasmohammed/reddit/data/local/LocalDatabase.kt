package com.devanasmohammed.reddit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devanasmohammed.reddit.data.model.Article

@Database(
    entities = [Article::class],
    version = 1,
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun articleDao() : ArticleDao

    companion object{
        @Volatile
        private var INSTANCE: LocalDatabase? = null
        fun getDatabase(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}