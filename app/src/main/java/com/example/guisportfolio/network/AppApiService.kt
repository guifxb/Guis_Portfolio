package com.example.guisportfolio.network

import com.example.guisportfolio.model.MovieInfoNet
import retrofit2.http.GET
import retrofit2.http.Query


//Public interface that exposes the getMovie() method

interface AppApiService {
    @GET("?apikey=2ec3f8a4")
    suspend fun getMovieInfo(
        @Query("i") movie : String
    ): MovieInfoNet
}