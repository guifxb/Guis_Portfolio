package com.example.guisportfolio.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.guisportfolio.R
import com.example.guisportfolio.R.drawable
import com.example.guisportfolio.model.MovieInfo

@Composable
fun GridScreen(
    uiState: AppUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailsButtonClicked: (MovieInfo) -> Unit,
) {

    when (uiState) {
        is AppUiState.Loading -> LoadingScreen(modifier)
        is AppUiState.Success -> PosterGrid(movies = uiState.movies,
            modifier,
            onDetailsButtonClicked = onDetailsButtonClicked)
        is AppUiState.Error -> ErrorScreen(retryAction, modifier)
    }
}

// Card for each poster
@Composable
fun PosterCard(
    movie: MovieInfo,
    modifier: Modifier = Modifier,
    onDetailsButtonClicked: (MovieInfo) -> Unit,


    ) {

    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxSize()
            .clickable(onClick = { onDetailsButtonClicked(movie) })

    ) {

        AsyncImage(model = ImageRequest.Builder(context = LocalContext.current).data(movie.poster)
            .crossfade(true).build(),
            modifier = modifier
                .fillMaxWidth()
                .clip(RectangleShape),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            error = painterResource(id = drawable.ic_broken_image),
            placeholder = painterResource(id = drawable.loading_img))

    }
}

@Composable
fun PosterGrid(
    movies: List<MovieInfo>,
    modifier: Modifier = Modifier,
    onDetailsButtonClicked: (MovieInfo) -> Unit,
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
    ) {
        items(items = movies, key = { movie -> movie.title }) { movie ->
                PosterCard(movie = movie, onDetailsButtonClicked = onDetailsButtonClicked)
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(text = stringResource(R.string.loading_text_loading_screen))
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(stringResource(R.string.failed_to_load_failed_screen))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry_failed_screen))
        }
    }
}
