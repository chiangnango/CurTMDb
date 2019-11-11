package com.example.curtmdb.main

import com.example.curtmdb.data.APIConfig
import com.example.curtmdb.data.PopularResult
import com.example.curtmdb.util.APIUtil
import com.example.curtmdb.util.APIUtil.tmdbService
import retrofit2.await

class MainRepository {

    suspend fun fetchTMDbConfiguration(): APIUtil.APIResult<APIConfig> {
        return try {
            val result = tmdbService.configuration().await()
            APIUtil.APIResult.Success(result)
        } catch (e: Exception) {
            APIUtil.APIResult.Failure(e)
        }
    }

    suspend fun fetchPopularMovies(page: Int = 1): APIUtil.APIResult<PopularResult> {
        return try {
            val result = tmdbService.popularMovies(page).await()
            APIUtil.APIResult.Success(result)
        } catch (e: Exception) {
            APIUtil.APIResult.Failure(e)
        }
    }
}