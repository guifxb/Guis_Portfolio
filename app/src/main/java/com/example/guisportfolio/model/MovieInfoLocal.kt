package com.example.guisportfolio.model

import androidx.room.Entity


@Entity(tableName = "movies", primaryKeys = ["title", "year"] )
data class MovieInfoLocal(
    val title: String,
    val year: String,
    val plot: String,
    val poster: String,
    val imdbid: String
)

val DefaultMovie: MovieInfoLocal = MovieInfoLocal( "Title", "Year", "Title plot or details", "https://m.media-amazon.com/images/M/MV5BMTUxMzk0NDg1MV5BMl5BanBnXkFtZTgwNDg0NjkxMDI@._V1_SX300.jpg", "tt1285016")