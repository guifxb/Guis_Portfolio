package com.example.guisportfolio.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.guisportfolio.AppApplication
import com.example.guisportfolio.data.MovieRepository
import com.example.guisportfolio.model.DefaultMovie
import com.example.guisportfolio.model.MovieInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface AppUiState {
    data class Success(val movies: List<MovieInfo>) : AppUiState
    object Error : AppUiState
    object Loading : AppUiState

}

class AppViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    var uiState: AppUiState by mutableStateOf(AppUiState.Loading)
        private set

    init {
        getMovieInfo()
    }

    private val _currentMovie = MutableStateFlow(DefaultMovie)
    val currentMovie: StateFlow<MovieInfo> = _currentMovie.asStateFlow()


    fun updateCurrentMovie(movieInfo: MovieInfo) {
        _currentMovie.update {
            movieInfo
        }
    }

    fun getMovieInfo() {
        viewModelScope.launch {
            uiState = AppUiState.Loading
            uiState = try {
                AppUiState.Success(movieRepository.getMovieInfo())
            } catch (e: IOException) {
                AppUiState.Error
            } catch (e: HttpException) {
                AppUiState.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AppApplication)
                val movieRepository = application.container.movieRepository
                AppViewModel(movieRepository = movieRepository)
            }
        }
    }

}