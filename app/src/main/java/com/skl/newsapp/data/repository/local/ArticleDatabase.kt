package com.skl.newsapp.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skl.newsapp.data.repository.local.model.ArticleRemoteKey
import com.skl.newsapp.data.repository.local.model.DomainArticle
import com.skl.newsapp.utils.Converters

@Database(entities = [DomainArticle::class, ArticleRemoteKey::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase(){
    abstract val articleDao: ArticleDao
}