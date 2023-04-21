package com.example.translatorsportfolio.data

import com.example.translatorsportfolio.model.MovieInfoNet
import com.example.translatorsportfolio.network.AppApiService

//Singleton to instance the online repository
class NetworkMovieRepository(
    private val appApiService: AppApiService,
) : MovieRepository {
    override suspend fun getMovieInfo(): List<MovieInfoNet> {
        val listToReturn: MutableList<MovieInfoNet> = mutableListOf()
        for (movie in PortfolioData.movies) listToReturn.add(appApiService.getMovieInfo(movie))
        return listToReturn
    }

    override suspend fun getTitleToAdd(titleSearch: String): MovieInfoNet {
        return appApiService.getMovieInfo(titleSearch)
    }
}