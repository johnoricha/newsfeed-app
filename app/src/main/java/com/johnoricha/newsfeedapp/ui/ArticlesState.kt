package com.johnoricha.newsfeedapp.ui

import com.johnoricha.newsfeedapp.domain.Article

data class ArticlesState(
    var articles: List<Article> = emptyList(),
    val isLoading: Boolean = false
)
