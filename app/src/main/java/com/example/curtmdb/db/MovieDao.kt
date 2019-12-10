package com.example.curtmdb.db

import androidx.paging.DataSource
import androidx.room.*
import com.example.curtmdb.data.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun moviesByPopularity(): DataSource.Factory<Int, Movie>

    @Update
    fun updateMovies(vararg movies: Movie)
}