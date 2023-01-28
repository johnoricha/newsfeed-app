package com.johnoricha.newsfeedapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articles: List<ArticleEntity>)

    @Query("DELETE FROM articleentity WHERE title IN(:titles)")
    suspend fun deleteArticles(titles: List<String?>)

    @Query("SELECT *  FROM articleentity")
    suspend fun getArticles(): List<ArticleEntity>
}