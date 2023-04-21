package com.example.translatorsportfolio.data

import com.example.translatorsportfolio.model.MovieInfoNet

interface MovieRepository {
    suspend fun getMovieInfo(): List<MovieInfoNet>
    suspend fun getTitleToAdd(titleSearch: String): MovieInfoNet
}

