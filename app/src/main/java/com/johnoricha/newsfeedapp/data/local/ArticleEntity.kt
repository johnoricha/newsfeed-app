package com.johnoricha.newsfeedapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.johnoricha.newsfeedapp.domain.Article


@Entity
data class ArticleEntity(
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val author: String?,
    @PrimaryKey val id: Int? = null
) {
    fun toArticle(): Article {
        return Article(
            title = title,
            url = url,
            urlToImage = urlToImage,
            author = author
        )
    }

}
