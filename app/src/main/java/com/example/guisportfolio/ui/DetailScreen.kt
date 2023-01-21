package com.example.guisportfolio.ui


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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.guisportfolio.R
import com.example.guisportfolio.model.DefaultMovie
import com.example.guisportfolio.model.MovieInfo
import com.example.guisportfolio.ui.theme.GuisPortfolioTheme


@Composable
fun DetailScreen(
    movieInfo: MovieInfo,
    modifier: Modifier = Modifier,
) {
    Column {
        PosterAndInfo(movieInfo = movieInfo)
        PlotAndLink(movieInfo = movieInfo)


    }
}


@Composable
private fun PosterAndInfo(
    movieInfo: MovieInfo,
    modifier: Modifier = Modifier,
) {


    Row {
        AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
            .data(movieInfo.poster).crossfade(true).build(),
            contentDescription = movieInfo.title,
            contentScale = ContentScale.FillBounds,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            modifier = Modifier
                .size(200.dp, 334.dp)
                .padding(8.dp))
        InfoCard(movieInfo = movieInfo)
    }
}


@Composable
private fun InfoCard(
    modifier: Modifier = Modifier,
    movieInfo: MovieInfo,
) {
    Column(Modifier.padding(top = 16.dp), horizontalAlignment = Alignment.Start) {
        Text(text = movieInfo.title,
            modifier = modifier.padding(top = 16.dp, start = 8.dp),
            style = MaterialTheme.typography.headlineMedium)
        Text(text = "(${movieInfo.year})",
            modifier = modifier.padding(top = 16.dp, start = 8.dp),
            style = MaterialTheme.typography.headlineSmall)

    }
}


@Composable
fun PlotAndLink(
    movieInfo: MovieInfo,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = movieInfo.plot,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Justify,
        )
        Spacer(modifier = Modifier.size(32.dp))
        AnnotatedClickableText(movieInfo = movieInfo)
    }
}


@Composable
fun AnnotatedClickableText(
    movieInfo: MovieInfo,
    modifier: Modifier = Modifier,
) {
    val movieUrl = "https://www.imdb.com/title/${movieInfo.imdbid}/"
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

fun AnnotatedString.onLinkClick(offset: Int, onClick: (String) -> Unit) {
    getStringAnnotations(start = offset, end = offset).firstOrNull()?.let {
        onClick(it.item)
    }
}


@Preview(showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    GuisPortfolioTheme() {
        DetailScreen(movieInfo = DefaultMovie)
    }
}