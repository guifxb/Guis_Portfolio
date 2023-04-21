package com.example.translatorsportfolio.ui.screens


import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.translatorsportfolio.R


@Composable
fun AboutAppScreen(modifier: Modifier = Modifier) {

    LazyColumn(verticalArrangement = Arrangement.Top, modifier = modifier.padding(16.dp)
    ) {
        item {
            Row {
                Spacer(modifier = modifier.weight(0.5f))
                Image(modifier = modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.android_jetpack_logo),
                    contentDescription = null)
            }
            Spacer(modifier = modifier.size(16.dp))

            TextBox(info = R.string.intro_about_app_text)
            Spacer(modifier = modifier.size(16.dp))
            TitleBox(info = R.string.design_about_app_title)
            Spacer(modifier = modifier.size(8.dp))
            TextBox(info = R.string.design_about_app_text)
            Spacer(modifier = modifier.size(16.dp))
            TitleBox(info = R.string.architecture_about_app_title)
            Spacer(modifier = modifier.size(8.dp))
            TextBox(info = R.string.architecture_about_app_text)
            Spacer(modifier = modifier.size(16.dp))
            TitleBox(info = R.string.libraries_about_app_title)
            Spacer(modifier = modifier.size(8.dp))
            TextBox(info = R.string.libraries_about_app_text)
            Spacer(modifier = modifier.size(16.dp))
            TitleBox(info = R.string.database_about_app_title)
            Spacer(modifier = modifier.size(8.dp))
            TextBox(info = R.string.database_about_app_text)
        }
    }
}

@Composable
fun TitleBox(@StringRes info: Int, modifier: Modifier = Modifier) {
    Text(text = stringResource(id = info), style = MaterialTheme.typography.headlineLarge)
}

@Composable
fun TextBox(@StringRes info: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = info),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Justify,
    )
}

@Preview(showSystemUi = true)
@Composable
fun AboutAppPreview() {
    AboutAppScreen()
}

