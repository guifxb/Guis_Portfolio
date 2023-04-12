package com.example.guisportfolio.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// Data class to define how to parse info from API
@Serializable
data class MovieInfoNet (
    @SerialName(value = "Title")
    val title: String,
    @SerialName(value = "Year")
    val year: String,
    @SerialName(value = "Plot")
    val plot: String,
    @SerialName(value = "Poster")
    val poster: String,
    @SerialName(value = "imdbID")
    val imdbid: String
    )

val DefaultTitleToAdd: MovieInfoNet = MovieInfoNet("Title", "Year", "Title plot or details", "https://via.placeholder.com/250x150/FFFFFF/000000/?text=Poster", "tt1285016")

val BrokenTitle: MovieInfoNet = MovieInfoNet("Something is broken here", "Current Year", "It seems that there was a error finding this title. It may be wrong, you may be offline or maybe there's something up with the servers. It's truly a mystery.", "?", "tt1285016")

//Bridging API and local classes
fun MovieInfoNet.toMovieInfoLocal(): MovieInfoLocal = MovieInfoLocal(
    title = title,
    year = year,
    plot = plot,
    poster = poster,
    imdbid = imdbid
)