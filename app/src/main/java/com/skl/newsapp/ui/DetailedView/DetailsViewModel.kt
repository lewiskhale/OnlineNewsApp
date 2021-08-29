package com.skl.newsapp.ui.DetailedView


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skl.newsapp.data.repository.ArticleRepository
import com.skl.newsapp.data.repository.local.model.DomainArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val repository: ArticleRepository
) :ViewModel() {

    private var _isSaved = MutableLiveData<Boolean>()
    val isSaved:LiveData<Boolean> get() = _isSaved

    fun saveArticle(article: DomainArticle){
        viewModelScope.launch {
            _isSaved.value = article.saved
            repository.updateArticle(article)
        }
    }
}