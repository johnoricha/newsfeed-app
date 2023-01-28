package com.johnoricha.newsfeedapp.data.remote.dto

import com.johnoricha.newsfeedapp.data.local.ArticleEntity

data class ArticleDto(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?
)

{
 fun toArticleEntity() : ArticleEntity {
     return ArticleEntity(
         title = title,
         author = author,
         url = url,
         urlToImage = urlToImage
     )
 }
}