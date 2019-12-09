package com.example.curtmdb.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.curtmdb.api.TMDbService
import com.example.curtmdb.data.Movie
import com.example.curtmdb.data.PopularResult
import com.example.curtmdb.db.MovieLocalCache
import com.example.curtmdb.util.APIUtil
import com.example.curtmdb.util.MyLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.await

class PopularMovieBoundaryCallback(
    private val scope: CoroutineScope,
    private val service: TMDbService,
    private val cache: MovieLocalCache
) : PagedList.BoundaryCallback<Movie>() {

    private var isRequestInProgress = false

    private val _apiException = MutableLiveData<Exception>()
    val apiException: LiveData<Exception>
        get() = _apiException

    override fun onZeroItemsLoaded() {
        MyLog.d(TAG, "onZeroItemsLoaded() ${Thread.currentThread()}")

        fetchData(1)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        MyLog.d(TAG, "onItemAtEndLoaded() pageAtEnd: ${itemAtEnd.page}, ${Thread.currentThread()}")

        if (itemAtEnd.page == LAST_PAGE) {
            MyLog.d(TAG, "no more pages to load...")
            return
        }

        val queryPage = itemAtEnd.page + 1
        fetchData(queryPage)
    }

    private fun fetchData(queryPage: Int) {
        if (isRequestInProgress) {
            return
        }

        isRequestInProgress = true

        scope.launch {
            when (val result = fetchPopularMovies(queryPage)) {
                is APIUtil.APIResult.Success -> {
                    MyLog.d(
                        TAG,
                        "${result.data.page} / ${result.data.totalPages}, totalResult: ${result.data.totalResults}"
                    )
                    val list = addPageInfo(result.data)
                    cache.insert(list)
                }
                is APIUtil.APIResult.Failure -> {
                    _apiException.value = result.exception
                }
            }
            isRequestInProgress = false
        }
    }

    private suspend fun fetchPopularMovies(page: Int): APIUtil.APIResult<PopularResult> {
        return try {
            val result = service.popularMovies(page).await()
            APIUtil.APIResult.Success(result)
        } catch (e: Exception) {
            APIUtil.APIResult.Failure(e)
        }
    }

    private fun addPageInfo(result: PopularResult): List<Movie> {
        return result.movies.map {
            val page = if (result.page == result.totalPages) {
                LAST_PAGE
            } else {
                result.page
            }
            it.copy(page = page)
        }
    }

    companion object {
        private val TAG = PopularMovieBoundaryCallback::class.java.simpleName

        private const val LAST_PAGE = -1
    }

}