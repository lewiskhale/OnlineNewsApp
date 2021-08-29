package com.skl.newsapp.data.repository.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleRemoteKey(

    @PrimaryKey(autoGenerate = false)
    val id: String,
    val next:Int?,
    val prev: Int?
)