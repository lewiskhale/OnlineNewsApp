package com.skl.newsapp.ui.Saved

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
class SavedViewModel @Inject constructor(
    val repository: ArticleRepository
): ViewModel() {

    private var _savedArticles = MutableLiveData<List<DomainArticle>>()
    val savedArticles: LiveData<List<DomainArticle>> get() = _savedArticles

    init {
        viewModelScope.launch {
            _savedArticles.value = repository.getSavedArticles()
        }
    }

}