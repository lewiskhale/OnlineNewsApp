package com.skl.newsapp.utils

import androidx.room.TypeConverter
import com.skl.newsapp.data.repository.network.model.Source

class Converters {

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name = name)
    }

    @TypeConverter
    fun fromSource(source: Source):String {
        return source.name
    }
}