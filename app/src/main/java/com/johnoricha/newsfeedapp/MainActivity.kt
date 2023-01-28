package com.johnoricha.newsfeedapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.johnoricha.newsfeedapp.ui.components.NewsfeedHomeScreen
import com.johnoricha.newsfeedapp.ui.theme.NewsfeedAppTheme
import com.johnoricha.newsfeedapp.ui.viewmodels.NewsfeedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsfeedAppTheme {
                // A surface container using the 'background' color from the theme
                val viewModel: NewsfeedViewModel = hiltViewModel()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NewsfeedHomeScreen(viewModel)
                }
            }
        }
    }
}

