package com.example.newsappexample.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsappexample.data.model.ArticlesItem

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articlesItem: ArticlesItem): Long
    @Query("SELECT COUNT(*) FROM ARTICLES WHERE url = :url")
    suspend fun countByUrl(url: String): Int

    @Query("SELECT * FROM ARTICLES")
    fun getAllNews(): LiveData<List<ArticlesItem>>

    @Delete
    suspend fun deleteNews(articlesItem: ArticlesItem)

}