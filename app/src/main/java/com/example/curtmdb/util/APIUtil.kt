package com.example.curtmdb.util

import com.example.curtmdb.BuildConfig
import com.example.curtmdb.api.TMDbService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIUtil {

    // TODO: hide api key
    const val API_KEY = "984d824a7e9963895e241560c899031a"
    const val URL = "https://api.themoviedb.org/3/"

    val tmdbService: TMDbService by lazy {
        createService()
    }

    fun createService(url: String = URL, okhttpClient: OkHttpClient = okHttpClient): TMDbService {
        return Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDbService::class.java)
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }.build()
    }
}