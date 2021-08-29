package com.skl.newsapp.data.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.skl.newsapp.data.repository.local.ArticleDao
import com.skl.newsapp.data.repository.local.ArticleDatabase
import com.skl.newsapp.data.repository.local.model.DomainArticle
import com.skl.newsapp.data.repository.network.NewsApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class RemoteMediator @Inject constructor(
    private val category: String = "",
    private val country: String = "",
    private val database: ArticleDatabase,
    private val newsApi: NewsApi
) : RemoteMediator<Int, DomainArticle>() {

    //TODO complete pagination
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DomainArticle>
    ): MediatorResult {
        TODO("Not yet implemented")
    }
}