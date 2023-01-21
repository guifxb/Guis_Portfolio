package com.example.guisportfolio.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// Data class to define a Movie info

@Serializable
data class MovieInfo (
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


val DefaultMovie: MovieInfo = MovieInfo("Samambaia", "1999", "Loucuras por loucos enlouquecidos", "https://m.media-amazon.com/images/M/MV5BOGUyZDUxZjEtMmIzMC00MzlmLTg4MGItZWJmMzBhZjE0Mjc1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg", "tt1285016")


