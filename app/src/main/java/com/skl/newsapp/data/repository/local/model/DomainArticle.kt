package com.skl.newsapp.data.repository.local.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skl.newsapp.data.repository.network.model.Source
import kotlinx.parcelize.Parcelize

@Entity(tableName = "articles")
@Parcelize
data class DomainArticle (
//
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
    @PrimaryKey(autoGenerate = false)
    val title: String,

    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    @Embedded(prefix = "source_")
    val source: Source,

    val url: String,
    val urlToImage: String?,
    var saved: Boolean,
    val lastUpdated: Long = System.currentTimeMillis()

): Parcelable