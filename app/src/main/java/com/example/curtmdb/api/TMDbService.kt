package com.example.curtmdb.api

import com.example.curtmdb.data.APIConfig
import com.example.curtmdb.data.PopularResult
import com.example.curtmdb.util.APIUtil
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbService {
    @GET("configuration?api_key=${APIUtil.API_KEY}")
    fun configuration(): Call<APIConfig>

    @GET("movie/popular?api_key=${APIUtil.API_KEY}&language=en-US")
    fun popularMovies(@Query("page") page: Int = 1): Call<PopularResult>
}