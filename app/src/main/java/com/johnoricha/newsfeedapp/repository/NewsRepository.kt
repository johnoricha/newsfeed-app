package com.johnoricha.newsfeedapp.repository

import com.johnoricha.newsfeedapp.core.Resource
import com.johnoricha.newsfeedapp.domain.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(): Flow<Resource<List<Article>>>

}