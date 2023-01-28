package com.johnoricha.newsfeedapp.domain

import com.johnoricha.newsfeedapp.core.Resource
import com.johnoricha.newsfeedapp.data.local.NewsDao
import com.johnoricha.newsfeedapp.data.remote.NewsApi
import com.johnoricha.newsfeedapp.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : NewsRepository {
    override fun getNews(): Flow<Resource<List<Article>>> = flow {

        emit(Resource.Loading())

        val localArticles = newsDao.getArticles()

        try {
            val remoteArticles =
                newsApi.getTopHeadlines()
            val titles = remoteArticles.articles.map {
                it.toArticleEntity().title
            }

            newsDao.deleteArticles(titles)

            newsDao.insertArticle(remoteArticles.articles.map {
                it.toArticleEntity()

            })
        } catch (e: HttpException) {

            emit(
                Resource.Error(
                    message = "oops! something went wrong",
                    data = localArticles.map { it.toArticle() }
                )
            )

        } catch (e: IOException) {

            emit(
                Resource.Error(
                    message = "Could not reach server",
                    data = localArticles.map { it.toArticle() }
                )
            )

        }

        val newArticles = newsDao.getArticles().map { it.toArticle() }
        emit(Resource.Success(data = newArticles))

    }
}