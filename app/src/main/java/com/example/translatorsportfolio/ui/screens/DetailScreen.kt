package com.example.translatorsportfolio.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.translatorsportfolio.R
import com.example.translatorsportfolio.model.MovieInfoLocal


@Composable
fun DetailScreen(
    movieInfoLocal: MovieInfoLocal,
    modifier: Modifier = Modifier,
) {
    Column {
        PosterAndInfo(movieInfoLocal = movieInfoLocal)
        PlotAndLink(movieInfoLocal = movieInfoLocal)
    }
}

@Composable
private fun PosterAndInfo(
    movieInfoLocal: MovieInfoLocal,
    modifier: Modifier = Modifier,
) {
    Row {
        AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
            .data(movieInfoLocal.poster).crossfade(true).build(),
            contentDescription = movieInfoLocal.title,
            contentScale = ContentScale.FillBounds,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            modifier = Modifier
                .size(200.dp, 334.dp)
                .padding(8.dp))
        InfoCard(movieInfoLocal = movieInfoLocal)
    }
}

@Composable
private fun InfoCard(
    modifier: Modifier = Modifier,
    movieInfoLocal: MovieInfoLocal,
) {
    Column(Modifier.padding(top = 16.dp), horizontalAlignment = Alignment.Start) {
        Text(text = movieInfoLocal.title,
            modifier = modifier.padding(top = 16.dp, start = 8.dp),
            style = MaterialTheme.typography.headlineMedium)
        Text(text = "(${movieInfoLocal.year})",
            modifier = modifier.padding(top = 16.dp, start = 8.dp),
            style = MaterialTheme.typography.headlineSmall)

    }
}

@Composable
fun PlotAndLink(
    movieInfoLocal: MovieInfoLocal,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = movieInfoLocal.plot,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Justify,
        )
        Spacer(modifier = Modifier.size(32.dp))
        AnnotatedClickableText(movieInfoLocal = movieInfoLocal)
    }
}

@Composable
fun AnnotatedClickableText(
    movieInfoLocal: MovieInfoLocal,
    modifier: Modifier = Modifier,
) {
    val movieUrl = "https://www.imdb.com/title/${movieInfoLocal.imdbid}/"
    val mUriHandler = LocalUriHandler.current
    val annotatedText = buildAnnotatedString {

        append("See on ")
        withStyle(style = SpanStyle(color = Color.Blue)) {
            appendLink("IMdb", movieUrl)
        }
    }

    ClickableText(text = annotatedText, onClick = {
        annotatedText.getStringAnnotations(movieUrl, it, it).firstOrNull()
            ?.let { stringAnnotation ->
                mUriHandler.openUri(stringAnnotation.item)
            }
    })
}

fun AnnotatedString.Builder.appendLink(linkText: String, linkUrl: String) {
    pushStringAnnotation(tag = linkUrl, annotation = linkUrl)
    append(linkText)
    pop()
}

