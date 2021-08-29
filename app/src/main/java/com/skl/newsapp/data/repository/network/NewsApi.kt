package com.skl.newsapp.data.repository.network

import com.skl.newsapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getBreakingNewsByCountry(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Response<NetworkResponse>

    @GET("top-headlines")
    suspend fun getBreakingNewsByCategory(
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Response<NetworkResponse>

    @GET("top-headlines")
    suspend fun getBreakingNewsByCategoryAndCountry(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Response<NetworkResponse>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Response<NetworkResponse>


}