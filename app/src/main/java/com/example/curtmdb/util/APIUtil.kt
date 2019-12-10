package com.example.curtmdb.util

object APIUtil {

    // TODO: hide api key
    const val API_KEY = "984d824a7e9963895e241560c899031a"
    private const val URL = "https://api.themoviedb.org/3/"

    fun getBaseUrl(): String {
        return URL
    }

    sealed class APIResult<T> {
        data class Success<T>(val data: T) : APIResult<T>()
        data class Failure<T>(val exception: Exception) : APIResult<T>()
    }
}