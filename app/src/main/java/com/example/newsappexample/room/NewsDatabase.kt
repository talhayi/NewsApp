package com.example.newsappexample.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappexample.data.model.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 2)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase(){
    abstract fun getNewsDao(): NewsDao
}