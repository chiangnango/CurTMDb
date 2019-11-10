package com.example.curtmdb.util

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object APIUtil {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    data class HttpException(
        val code: Int,
        val msg: String,
        val body: String?
    ) : Exception("HTTP $code $msg")
}