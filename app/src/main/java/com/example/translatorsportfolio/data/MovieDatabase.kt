package com.example.translatorsportfolio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.translatorsportfolio.model.MovieInfoLocal



//Database class with a singleton INSTANCE object.
@Database(entities = [MovieInfoLocal::class], version = 3, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var Instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}