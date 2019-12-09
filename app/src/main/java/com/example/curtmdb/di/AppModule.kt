package com.example.curtmdb.di

import android.content.Context
import com.example.curtmdb.BuildConfig
import com.example.curtmdb.api.TMDbService
import com.example.curtmdb.db.MovieDao
import com.example.curtmdb.db.MovieDatabase
import com.example.curtmdb.util.APIUtil
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideTMDbService(okhttpClient: OkHttpClient): TMDbService {
        return Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl(APIUtil.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDbService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(logger)
                }
            }.build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideMovieDataBase(context: Context): MovieDatabase {
        return MovieDatabase.getInstance(context)
    }
}