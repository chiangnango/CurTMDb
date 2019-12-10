package com.example.curtmdb.util

import com.example.curtmdb.BuildConfig

object APIUtil {

    const val API_KEY: String = BuildConfig.apikey

    private const val URL = "https://api.themoviedb.org/3/"

    fun getBaseUrl(): String {
        return URL
    }

    sealed class APIResult<T> {
        data class Success<T>(val data: T) : APIResult<T>()
        data class Failure<T>(val exception: Exception) : APIResult<T>()
    }
}