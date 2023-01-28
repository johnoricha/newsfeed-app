package com.johnoricha.newsfeedapp

import com.google.common.truth.Truth
import com.johnoricha.newsfeedapp.core.Resource
import com.johnoricha.newsfeedapp.domain.Article
import com.johnoricha.newsfeedapp.repository.NewsRepository
import com.johnoricha.newsfeedapp.ui.viewmodels.NewsfeedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify


class NewsfeedViewModelTest {

    private val newsfeedRepositoryMock = mock(NewsRepository::class.java)
    private lateinit var newsfeedViewModel: NewsfeedViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Before
    fun setUp() {
        newsfeedViewModel = NewsfeedViewModel(newsfeedRepositoryMock)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun get_news_returns_list_of_articles() {

        runTest {
            val expectedArticles = listOf(
                Article(
                    title = "title",
                    author = "author",
                    url = "url",
                    urlToImage = "urlToImage"
                ),
                Article(
                    title = "title2",
                    author = "author2",
                    url = "url2",
                    urlToImage = "urlToImage2"
                ),
            )

            `when`(newsfeedRepositoryMock.getNews()).thenReturn(flow {
                emit(Resource.Success(data = expectedArticles))
            })

            newsfeedRepositoryMock.getNews().collect {
                newsfeedViewModel.articlesState.value.articles = it.data!!
            }

            verify(newsfeedRepositoryMock, atLeastOnce()).getNews()


            Truth.assertThat(newsfeedViewModel.articlesState.value.articles)
                .isEqualTo(expectedArticles)
        }

    }
}