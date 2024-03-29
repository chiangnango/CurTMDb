package com.example.curtmdb.main

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.curtmdb.api.TMDbService
import com.example.curtmdb.data.APIConfig
import com.example.curtmdb.data.Movie
import com.example.curtmdb.db.MovieLocalCache
import com.example.curtmdb.util.APIUtil
import kotlinx.coroutines.CoroutineScope
import retrofit2.await
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val service: TMDbService,
    private val cache: MovieLocalCache
) {

    suspend fun fetchTMDbConfiguration(): APIUtil.APIResult<APIConfig> {
        return try {
            val result = service.configuration().await()
            APIUtil.APIResult.Success(result)
        } catch (e: Exception) {
            APIUtil.APIResult.Failure(e)
        }
    }

    fun fetchPopularMovies(scope: CoroutineScope): PopularMovieResult {
        val dataSourceFactory = cache.moviesByPopularity()
        val boundaryCallback = PopularMovieBoundaryCallback(scope, service, cache)
        val networkException = boundaryCallback.apiException
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return PopularMovieResult(data, networkException)
    }

    suspend fun updateMovie(movie: Movie) {
        cache.update(movie)
    }

    data class PopularMovieResult(
        val data: LiveData<PagedList<Movie>>,
        val exception: LiveData<Exception>
    )

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}