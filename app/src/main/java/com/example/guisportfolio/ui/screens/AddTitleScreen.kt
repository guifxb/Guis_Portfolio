package com.example.guisportfolio.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.guisportfolio.R
import com.example.guisportfolio.model.BrokenTitle
import com.example.guisportfolio.model.DefaultTitleToAdd
import com.example.guisportfolio.model.MovieInfoNet


@Composable
fun AddTitleScreen(
    addTitleCurrentMovie: MovieInfoNet,
    modifier: Modifier = Modifier,
    onSearchButtonClicked: (String) -> Unit,
    onAddButtonClicked: () -> Unit,
    ) {
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top) {
        PosterAndInfo(movieInfoLocal = addTitleCurrentMovie)
        Plot(movieInfoLocal = addTitleCurrentMovie)
        Spacer(modifier.weight(1f, true))
        SaveButtons(movieInfoLocal = addTitleCurrentMovie,
            onSearchButtonClicked = onSearchButtonClicked,
            onAddButtonClicked = onAddButtonClicked)
    }
}

@Composable
private fun PosterAndInfo(
    movieInfoLocal: MovieInfoNet,
    modifier: Modifier = Modifier,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
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
    movieInfoLocal: MovieInfoNet,
) {
    Column(Modifier
        .fillMaxWidth()
        .padding(top = 16.dp), horizontalAlignment = Alignment.Start) {
        Text(text = movieInfoLocal.title,
            modifier = modifier.padding(top = 16.dp, start = 8.dp),
            style = MaterialTheme.typography.headlineMedium)
        Text(text = "(${movieInfoLocal.year})",
            modifier = modifier.padding(top = 16.dp, start = 8.dp),
            style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun Plot(
    movieInfoLocal: MovieInfoNet,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = movieInfoLocal.plot,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Justify,
        )
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveButtons(
    modifier: Modifier = Modifier,
    movieInfoLocal: MovieInfoNet,
    onSearchButtonClicked: (String) -> Unit,
    onAddButtonClicked: () -> Unit,
) {
    val savedToast = Toast.makeText(LocalContext.current, stringResource(R.string.title_save_toast), Toast.LENGTH_LONG)
    val errorToast = Toast.makeText(LocalContext.current, stringResource(R.string.title_not_found_toast), Toast.LENGTH_LONG)
    var openDialogAdd by remember { mutableStateOf(false) }
    var textField by remember { mutableStateOf("") }
    var saveButtonBool by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(modifier
        .padding(16.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        OutlinedTextField(
            value = textField,
            onValueChange = { textField = it },
            label = { Text("IMDB id") },
            placeholder = { Text(text = stringResource(R.string.exemple_placeholder), modifier = Modifier.alpha(0.35f)) },
            singleLine = true,
            trailingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )

        Row(modifier
            .padding(16.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                onSearchButtonClicked(textField)
                if (movieInfoLocal == BrokenTitle) {
                    errorToast.show()
                }
            }) { Text(text = stringResource(R.string.search_button)) }

            Button(onClick = { openDialogAdd = true }, enabled = saveButtonBool) {
                Text(text = stringResource(R.string.save_button))
            }
        }
    }

    saveButtonBool = movieInfoLocal != DefaultTitleToAdd && movieInfoLocal != BrokenTitle

    if (openDialogAdd) {
        AlertDialog(
            onDismissRequest = {
                openDialogAdd = false
            },
            title = {
                Text(text = stringResource(id = R.string.add_title_alert_title))
            },
            text = {
                Text(text = stringResource(R.string.add_title_alert_text) + movieInfoLocal.title + "?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAddButtonClicked()
                        openDialogAdd = false
                        savedToast.show()
                        textField = ""
                    }
                ) {
                    Text(stringResource(id = R.string.just_yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialogAdd = false
                    }
                ) {
                    Text(stringResource(id = R.string.just_no))
                }
            }
        )
    }
}



