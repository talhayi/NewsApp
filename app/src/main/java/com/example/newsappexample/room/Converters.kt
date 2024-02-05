package com.example.newsappexample.room

import androidx.room.TypeConverter
import com.example.newsappexample.data.model.news.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String?{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String/*, id: String*/): Source {
        return Source(name, name)
    }
}