package com.example.translatorsportfolio.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.translatorsportfolio.R
import com.example.translatorsportfolio.R.drawable
import com.example.translatorsportfolio.model.MovieInfoLocal
import com.example.translatorsportfolio.ui.managers.AppUiState


@Composable
fun GridScreen(
    uiState: AppUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailsButtonClicked: (MovieInfoLocal) -> Unit,
    onAddButtonClicked: () -> Unit,
    onDeleteButtonClicked: (MutableList<MovieInfoLocal>) -> Unit,
) {
    when (uiState) {
        is AppUiState.Loading -> LoadingScreen(modifier)
        is AppUiState.Success -> PosterGrid(movies = uiState.movies,
            modifier,
            onDetailsButtonClicked = onDetailsButtonClicked,
            onAddButtonClicked = onAddButtonClicked,
            onDeleteButtonClicked = onDeleteButtonClicked)
        is AppUiState.Error -> ErrorScreen(retryAction, modifier)
    }
}

// Card for each poster
@Composable
fun PosterCard(
    movie: MovieInfoLocal,
    modifier: Modifier = Modifier,
    onDetailsButtonClicked: (MovieInfoLocal) -> Unit,
    showCheckbox: Boolean,
    checkedState: Boolean,
    onCheckedStateChanged: (List<Boolean>) -> Unit,
    checkedStates: SnapshotStateList<Boolean>,
    index: Int
) {
    Surface() {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onDetailsButtonClicked(movie) }
            .padding(2.dp)
        ) {
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(movie.poster)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .crossfade(true).build(),
                modifier = modifier
                    .clickable { onDetailsButtonClicked(movie) }
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(9f / 16f)
                    .alpha(if (showCheckbox) 0.75f else 1f),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                error = painterResource(id = drawable.ic_broken_image),
                placeholder = painterResource(id = drawable.loading_img))

            if (showCheckbox) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = { isChecked ->
                        onCheckedStateChanged(
                            checkedStates.toMutableList().apply {
                                set(index, isChecked)
                            })
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(30.dp)
                        .graphicsLayer(2f, 2f),
                    colors = CheckboxDefaults.colors(uncheckedColor = Color.Black)
                )
            }
        }
    }
}

//grid composable
@Composable
fun PosterGrid(
    movies: List<MovieInfoLocal>,
    modifier: Modifier = Modifier,
    onDetailsButtonClicked: (MovieInfoLocal) -> Unit,
    onAddButtonClicked: () -> Unit,
    onDeleteButtonClicked: (MutableList<MovieInfoLocal>) -> Unit,
) {
    val showCheckbox = remember { mutableStateOf(false) }
    val checkedStates = remember { List(movies.size) { false }.toMutableStateList() }
    val moviesToDelete = movies.filterIndexed { index, movie -> checkedStates.getOrNull(index) == true }.toMutableList()
    var openDialogDelete by remember { mutableStateOf(false) }
    val deletedToast = Toast.makeText(LocalContext.current, stringResource(R.string.deleted_toast), Toast.LENGTH_LONG)
    val baseCheckedState = List(movies.size) { false }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
    ) {
        itemsIndexed(items = movies) { index, movie ->
            PosterCard(movie = movie,
                checkedStates = checkedStates,
                onDetailsButtonClicked = onDetailsButtonClicked,
                showCheckbox = showCheckbox.value,
                checkedState = checkedStates.getOrNull(index) == true,
                onCheckedStateChanged = { newCheckedStates ->
                    checkedStates.clear()
                    checkedStates.addAll(newCheckedStates)
                },
                index = index)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
    ) {

        //ADD button
        FloatingActionButton(
            onClick = onAddButtonClicked,
            containerColor = MaterialTheme.colorScheme.primary,
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(R.string.add_title_content_description),
                tint = Color.White,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        //DELETE button
        if (showCheckbox.value) {
            FloatingActionButton(
                onClick = {
                    if (moviesToDelete.isNotEmpty()) openDialogDelete = true
                    showCheckbox.value = !showCheckbox.value
                          },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    imageVector = Icons.Outlined.DeleteForever,
                    contentDescription = stringResource(R.string.delete_content_description),
                    tint = Color.White,
                )
            }
        } else {
            FloatingActionButton(
                onClick = { showCheckbox.value = !showCheckbox.value },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.delete_content_description),
                    tint = Color.White,
                )
            }
        }
    }
    if (openDialogDelete) {
        AlertDialog(
            onDismissRequest = {
                openDialogDelete = false
                checkedStates.clear()
                checkedStates.addAll(baseCheckedState)
            },
            title = {
                Text(text = stringResource(R.string.delete_title_alert_title))
            },
            text = {
                Text(text = stringResource(R.string.alert_confirm_text_1) + moviesToDelete.size + stringResource(
                                    R.string.alert_confirm_text_2))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteButtonClicked(moviesToDelete)
                        openDialogDelete = false
                        deletedToast.show()
                    }
                ) {
                    Text(stringResource(R.string.just_yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialogDelete = false
                        checkedStates.clear()
                        checkedStates.addAll(baseCheckedState)
                    }
                ) {
                    Text(stringResource(R.string.just_no))
                }
            }
        )
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
