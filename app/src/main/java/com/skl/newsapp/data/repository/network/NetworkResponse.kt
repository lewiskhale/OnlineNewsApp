package com.skl.newsapp.data.repository.network

import com.skl.newsapp.data.repository.network.model.NetworkArticle

data class NetworkResponse(
    val articles: List<NetworkArticle>,
    val status: String,
    val totalResults: Int
)