package com.example.curtmdb.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.curtmdb.data.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun moviesByPopularity(): DataSource.Factory<Int, Movie>

    @Query("SELECT COUNT(*) FROM movies")
    fun movieCount(): Int
}