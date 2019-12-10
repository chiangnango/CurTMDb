package com.example.curtmdb.db

import androidx.paging.DataSource
import com.example.curtmdb.data.Movie
import com.example.curtmdb.util.MyLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieLocalCache @Inject constructor(private val movieDao: MovieDao) {

    companion object {
        private val TAG = MovieLocalCache::class.java.simpleName
    }

    suspend fun insert(movies: List<Movie>) {
        GlobalScope.launch(Dispatchers.IO) {
            MyLog.d(TAG, "inserting ${movies.size} movies")
            movieDao.insert(movies)
        }
    }

    fun moviesByPopularity(): DataSource.Factory<Int, Movie> {
        return movieDao.moviesByPopularity()
    }

    suspend fun update(movie: Movie) {
        GlobalScope.launch(Dispatchers.IO) {
            MyLog.d(TAG, "updating $movie")
            movieDao.updateMovies(movie)
        }
    }
}