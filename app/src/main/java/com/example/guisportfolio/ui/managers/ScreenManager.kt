package com.example.guisportfolio.ui.managers

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.guisportfolio.R
import com.example.guisportfolio.ui.screens.*


enum class AppScreen(@StringRes val title: Int) {
    Start(title = R.string.home_title),
    Grid(title = R.string.grid_page),
    Details(title = R.string.details_page),
    AboutApp(title = R.string.about_app),
    AddTitleScreen(title = R.string.add_new_title)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(

        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button))
                }
            }
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    // Get name of current screen
    val currentScreen =
        AppScreen.valueOf(backStackEntry?.destination?.route ?: AppScreen.Start.name)

    // ViewModel Constructor
    val onlineViewModel: OnlineViewModel = viewModel(factory = OnlineViewModel.Factory)

    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        AppBar(currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() })
    }
    ) {
        Surface(modifier = modifier
            .fillMaxSize()
            .padding(it)) {

            NavHost(navController = navController, startDestination = AppScreen.Start.name) {
                composable(route = AppScreen.Start.name) {
                    HomeScreen(onAboutButtonClicked = {
                        navController.navigate(AppScreen.AboutApp.name)
                    }, onPortfolioButtonClicked = {
                        navController.navigate(AppScreen.Grid.name)
                    }
                    )
                }

                composable(route = AppScreen.Grid.name) {
                    GridScreen(
                        uiState = onlineViewModel.uiState,
                        retryAction = onlineViewModel::getMovieInfo,
                        onDetailsButtonClicked = {
                            onlineViewModel.updateCurrentMovie(it)
                            navController.navigate(AppScreen.Details.name)
                        },
                        onAddButtonClicked = {
                            navController.navigate(AppScreen.AddTitleScreen.name)
                        },
                        onDeleteButtonClicked = {
                            onlineViewModel.deleteTitle(it)
                        }
                    )
                }

                composable(route = AppScreen.Details.name) {
                    val state = onlineViewModel.currentMovie.collectAsState()
                    DetailScreen(movieInfoLocal = state.value)
                }

                composable(route = AppScreen.AboutApp.name) {
                    AboutAppScreen()
                }

                composable(route = AppScreen.AddTitleScreen.name) {
                    val state = onlineViewModel.addTitleCurrentMovie.collectAsState()
                    AddTitleScreen(
                        addTitleCurrentMovie = state.value,
                        onSearchButtonClicked = {
                            onlineViewModel.getTitleToAdd(it)
                        }
                    ) {
                        onlineViewModel.addTitle()
                    }
                }
            }
        }
    }

}

