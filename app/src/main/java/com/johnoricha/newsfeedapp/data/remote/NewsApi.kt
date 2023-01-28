package com.johnoricha.newsfeedapp.data.remote

import com.johnoricha.newsfeedapp.data.remote.dto.ArticleDto
import com.johnoricha.newsfeedapp.data.remote.dto.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL: String = "https://newsapi.org/v2/"
const val API_KEY: String = "2d021085c2e64c23927ff485d9f4299b"

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsDto
}