package com.example.guisportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.guisportfolio.ui.theme.GuisPortfolioTheme
import com.example.guisportfolio.ui.managers.PortfolioApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuisPortfolioTheme() {
                PortfolioApp()
            }
        }
    }
}


@Preview
@Composable
fun DefaultScreen() {
    GuisPortfolioTheme {
        PortfolioApp()
    }
}