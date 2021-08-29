package com.skl.newsapp.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.skl.newsapp.data.repository.local.ArticleDatabase
import com.skl.newsapp.data.repository.local.model.DomainArticle
import com.skl.newsapp.data.repository.network.NewsApi
import com.skl.newsapp.data.repository.network.NetworkResponse
import com.skl.newsapp.data.repository.network.model.NetworkArticleMapper
import com.skl.newsapp.utils.Mapper
import com.skl.newsapp.utils.Resource
import com.skl.newsapp.utils.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val database: ArticleDatabase,
    private val newsApi: NewsApi
) {

    @ExperimentalCoroutinesApi
    fun getAllBreakingNews(countryCode: String, page: Int): Flow<Resource<List<DomainArticle>>> = networkBoundResource(
        queryDB = {
            val roomArticle = database.articleDao.getNonSavedArticles()
            Log.i("TAG", "getAllBreakingNews: db has these adressses - ${roomArticle}}")
            roomArticle
        },
        fetchFromWeb = {
            val response = newsApi.getBreakingNewsByCountry(country = countryCode, page = page)
            Log.i("TAG", "getAllBreakingNews: response is: ${response.isSuccessful}")
            response
        },
        saveCallResult = { response ->  //taken from the fetch request
            if (response.isSuccessful) {
                Log.i("TAG", "getAllBreakingNews: response load is successful")
                val newsResponse = response.body()
                if (newsResponse != null) {
//                    Log.i("TAG", "getAllBreakingNews: response body is not null :${response.body()}")
                    val list = NetworkArticleMapper().fromNetworkList(newsResponse.articles)
                    Log.i("TAG", "getAllBreakingNews: response has been converted to: $list")
                    if (!list.isNullOrEmpty()) {
                        dbTransfer(list)
                    }
                }
            } else {
                Log.i("TAG", "getAllBreakingNews: is null")
            }
        })

    suspend fun getSearchResults(query: String, page: Int): Flow<Resource<List<DomainArticle>>> {
        val response = newsApi.searchNews(query, page = page)
        var result: List<DomainArticle> = emptyList()
        try {
            Resource.Loading(response.body())
            if (response.isSuccessful) {
                response.body()?.let { list ->
                    result = NetworkArticleMapper().fromNetworkList(list.articles)
                    Log.i("TAG", "getSearchResults successfully fetched data ")
                    return flowOf(Resource.Success(result))
                }
            }
        } catch (e: Throwable) {
            Log.i("TAG", "getSearchResults failed to fetch data ")
            return flowOf(Resource.Error(response.message()))
        }
        return flow { Resource.Success(result) }
    }

    private suspend fun dbTransfer(list: List<DomainArticle>) {
        database.withTransaction {
            Log.i("TAG", "getAllBreakingNews: deleting from db")
            database.articleDao.deleteAllNonSaved()
            Log.i("TAG", "getAllBreakingNews: saving to db")
            database.articleDao.saveArticleList(list)
        }
    }

    suspend fun updateArticle(article: DomainArticle){
        if(article.saved){
            database.articleDao.upsert(article)
        }else{
            database.articleDao.deleteArticle(article.title)
        }
    }

    suspend fun getSavedArticles(): List<DomainArticle>
    {
        return database.articleDao.getSavedArticles()
    }

//    suspend fun searchHandling(query: String, page: Int): Resource<List<RoomArticle>> {
//        try {
//            val response = newsApi.searchNews(query = query, page = page)
//            Resource.Success(response.body())
//        } catch (error: Throwable) {
//            Resource.Error(response.message())
//        }
//    }
}
