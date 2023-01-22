package com.example.guisportfolio.data

import com.example.guisportfolio.network.AppApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val movieRepository: MovieRepository
}


class DefaultAppContainer : AppContainer {

    private val BASE_URL = "https://www.omdbapi.com"
    private val json = Json { ignoreUnknownKeys = true }

    //Retrofit builder
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL).build()


    //Retrofit service object for creating api calls
    private val retrofitService: AppApiService by lazy {
        retrofit.create(AppApiService::class.java)
    }

    override val movieRepository: MovieRepository by lazy {
        NetworkMovieRepository(retrofitService)
    }


}