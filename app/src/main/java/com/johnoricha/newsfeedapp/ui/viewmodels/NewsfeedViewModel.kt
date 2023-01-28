package com.johnoricha.newsfeedapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnoricha.newsfeedapp.core.Resource
import com.johnoricha.newsfeedapp.repository.NewsRepository
import com.johnoricha.newsfeedapp.ui.ArticlesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsfeedViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {


    private val _articlesState: MutableStateFlow<ArticlesState> = MutableStateFlow(ArticlesState())
    val articlesState = _articlesState.asStateFlow()

    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            newsRepository.getNews().collect { resource ->


                when (resource) {

                    is Resource.Loading -> {

                        _articlesState.value =
                            articlesState.value.copy(
                                articles = resource.data ?: emptyList(),
                                isLoading = true
                            )


                    }

                    is Resource.Success -> {
                        _articlesState.value =
                            articlesState.value.copy(
                                articles = resource.data ?: emptyList(),
                                isLoading = false
                            )

                    }
                    is Resource.Error -> {
                        _articlesState.value =
                            articlesState.value.copy(
                                articles = resource.data ?: emptyList(),
                                isLoading = false
                            )

                    }
                }
            }

        }
    }

}