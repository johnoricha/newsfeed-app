package com.johnoricha.newsfeedapp.ui.components

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.johnoricha.newsfeedapp.R
import com.johnoricha.newsfeedapp.ui.viewmodels.NewsfeedViewModel


const val CHROME_PACKAGE_NAME = "com.android.chrome"

@Composable
fun NewsfeedHomeScreen(
    viewModel: NewsfeedViewModel
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val context = LocalContext.current

    DisposableEffect(systemUiController, useDarkIcons) {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setStatusBarColor(
            color = Color.Gray,
            darkIcons = useDarkIcons
        )

        onDispose {}
    }

    val state = viewModel.articlesState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title =
                {
                    Text(
                        text = "Newsfeed",
                        color = Color.White
                    )
                },
                backgroundColor = Color.DarkGray
            )
        }
    ) {
        LazyColumn(
            Modifier
                .background(color = Color.DarkGray)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            item {
                Text(
                    text = "Top News",
                    color = Color.White,
                    modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
                    fontSize = 12.sp
                )
            }
            items(state.value.articles) { article ->
                ArticleItem(article = article) {

                    article.url?.let { url ->
                        openTab(context, url)
                    }

                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }


    }

}

fun openTab(context: Context, url: String) {

    val packageName = CHROME_PACKAGE_NAME

    val builder = CustomTabsIntent.Builder()

    builder.setShowTitle(true)

    builder.setInstantAppsEnabled(true)

    builder.setToolbarColor(ContextCompat.getColor(context, R.color.gray))

    val customTabsIntent = builder.build()

    customTabsIntent.intent.setPackage(packageName)

    customTabsIntent.launchUrl(context, Uri.parse(url))
}