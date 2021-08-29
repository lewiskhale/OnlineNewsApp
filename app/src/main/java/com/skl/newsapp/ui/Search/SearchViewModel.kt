package com.skl.newsapp.ui.Search

import android.util.Log
import androidx.lifecycle.*
import com.skl.newsapp.data.repository.ArticleRepository
import com.skl.newsapp.data.repository.local.model.DomainArticle
import com.skl.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    private var _searchedNewsArticles = searchQuery.flatMapLatest { query ->
        if (query.isBlank()) {
            flow { null }
        } else {
            Log.i("TAG", "the query that is being loaded is: ${query}")
            repository.getSearchResults(query, page = 1)
        }
    }

    @ExperimentalCoroutinesApi
    val searchedNewsArticles: LiveData<Resource<List<DomainArticle>>>
        get() = _searchedNewsArticles.asLiveData()

}