package com.example.guisportfolio.data

import com.example.guisportfolio.model.MovieInfoNet

interface MovieRepository {
    suspend fun getMovieInfo(): List<MovieInfoNet>
    suspend fun getTitleToAdd(titleSearch: String): MovieInfoNet
}

