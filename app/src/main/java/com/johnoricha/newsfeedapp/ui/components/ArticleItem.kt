package com.johnoricha.newsfeedapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.johnoricha.newsfeedapp.domain.Article


@Composable
fun ArticleItem(
    article: Article,
    onItemClick: () -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 2.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            contentScale = Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick() },
            colorFilter = ColorFilter.tint(
                Color.Black.copy(alpha = 0.5f),
                blendMode = BlendMode.Darken
            )
        )
        article.apply {

            Column {
                article.title?.let {
                    Text(
                        text = it,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                article.author?.let {
                    Text(
                        text = it,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

        }
    }

}
