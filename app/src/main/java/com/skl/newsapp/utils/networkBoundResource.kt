package com.skl.newsapp.utils

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

// ResultType: Type for the Resource data.  local db
// RequestType: Type for the API response.  webservice

//RequestType: Response<NewsResponse>
//ResultType: Flow<List<RoomArticle>>

@ExperimentalCoroutinesApi
inline fun <RequestType, ResultType> networkBoundResource(
    crossinline queryDB: () -> Flow<ResultType>,                   //calls the cached data
    crossinline fetchFromWeb: suspend () -> RequestType,            //tries to request new data from web
    crossinline saveCallResult: suspend (RequestType) -> Unit,      //saves the requested data from web to db
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    onFetchedFailed: Unit? = null
) = flow {
    val data = queryDB().first()
//    Log.i("TAG", "networkBoundResource: resultType from db is ${data}")

    val flow = if (shouldFetch(data)) {
        queryDB().map { Resource.Loading(it) }
//        val callfromWeb = fetchFromWeb()
//        Log.i("TAG", "networkBoundResource: callfromweb: ${callfromWeb}")
        try {
            saveCallResult(fetchFromWeb())
            queryDB().map { articleList ->
                Resource.Success(articleList)
            }
        } catch (error: Throwable) {
            Log.i("TAG", "networkBoundResource: error->${error}")
            queryDB().map { articleList ->
                Resource.Error(data = articleList, error = error.toString())
            }
        }
    } else {
//    queryDB().collect { articleList ->
//        Resource.Success(articleList) }
        queryDB().map { flowOfListOfRoomArticles ->
            Resource.Success(flowOfListOfRoomArticles)
        }
    }
    emitAll(flow)
}

