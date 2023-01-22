package com.example.guisportfolio.data

import com.example.guisportfolio.model.MovieInfo
import com.example.guisportfolio.network.AppApiService

interface MovieRepository {
    suspend fun getMovieInfo(): List<MovieInfo>

}

class NetworkMovieRepository(
    private val appApiService: AppApiService,
) : MovieRepository {
    override suspend fun getMovieInfo(): List<MovieInfo> {
        val listToReturn: MutableList<MovieInfo> = mutableListOf()
        for (movie in PortfolioData.movies) listToReturn.add(appApiService.getMovieInfo(movie))
        return listToReturn

    }

}

