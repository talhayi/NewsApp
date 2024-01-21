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
    //eklemek istediğimiz article varsa çakışma olmaması için
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articlesItem: ArticlesItem): Long // neden long dönüyoruz?

    @Query("SELECT * FROM ARTICLES")
    fun getAllNews(): LiveData<List<ArticlesItem>> //List<ArticlesItem> yerine NewsResponse dönmeyi dene //Livedata olmadan dene

    @Delete
    suspend fun deleteNews(articlesItem: ArticlesItem)

}