package com.skl.newsapp.data.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skl.newsapp.data.repository.local.model.ArticleRemoteKey
import com.skl.newsapp.data.repository.local.model.DomainArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveArticleList(list: List<DomainArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: DomainArticle): Long

    @Query("select * from articles where saved != 1")
    fun getNonSavedArticles(): Flow<List<DomainArticle>>

    //deletes all non saved articles
    @Query("delete from articles where saved != 1")
    suspend fun deleteAllNonSaved()

    //deletes a specific saved article
    @Query("delete from articles where title = :title")
    suspend fun deleteArticle(title: String)

    @Query("select * from articles where saved = 1")
    suspend fun getSavedArticles(): List<DomainArticle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(list: List<ArticleRemoteKey>)

    @Query("select * from ArticleRemoteKey where id = :id")
    suspend fun getAllRemoteKeys(id: String): ArticleRemoteKey?

    @Query("delete from articleremotekey")
    suspend fun deleteAllRemoteKeys()
}