package com.johnoricha.newsfeedapp.di

import android.app.Application
import androidx.room.Room
import com.johnoricha.newsfeedapp.data.local.NewsDatabase
import com.johnoricha.newsfeedapp.data.remote.BASE_URL
import com.johnoricha.newsfeedapp.data.remote.NewsApi
import com.johnoricha.newsfeedapp.repository.NewsRepository
import com.johnoricha.newsfeedapp.domain.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NewsfeedModule {

    @Provides
    @Singleton
    fun providesNewsRepository(
        api: NewsApi,
        db: NewsDatabase
    ): NewsRepository {
        return NewsRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun providesDictionaryApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(
                NewsApi::class.java
            )
    }

    @Provides
    @Singleton
    fun providesNewsDatabase(app: Application): NewsDatabase {
        return Room.databaseBuilder(
            app,
            NewsDatabase::class.java,
            "news_db"
        )
            .build()
    }
}