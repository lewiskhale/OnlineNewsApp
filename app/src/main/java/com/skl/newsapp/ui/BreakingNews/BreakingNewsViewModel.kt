package com.skl.newsapp.ui.BreakingNews

import androidx.lifecycle.*
import com.skl.newsapp.data.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    val isRefreshing = false

    val countryCode = MutableStateFlow("za")
    val category: String? = null
    var breakingNewsPage = 1

    @ExperimentalCoroutinesApi
    val breakingNewsArticles = countryCode.flatMapLatest {countryCode ->
        val result = repository.getAllBreakingNews(countryCode, breakingNewsPage)
        result
    }.asLiveData()

}

sealed class Event {
    data class ShowErrorMessage(val error: Throwable) : Event()
}