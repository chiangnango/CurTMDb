package com.example.curtmdb.architecture

import android.content.Context
import com.example.curtmdb.db.MovieDatabase
import com.example.curtmdb.db.MovieLocalCache
import com.example.curtmdb.main.MainRepository
import com.example.curtmdb.main.MainViewModelFactory
import com.example.curtmdb.util.APIUtil.tmdbService

object InjectorUtil {

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        return MainViewModelFactory(provideMainRepository(context))
    }

    private fun provideMovieCache(context: Context): MovieLocalCache {
        val database = MovieDatabase.getInstance(context)
        return MovieLocalCache(database.movieDao())
    }

    private fun provideMainRepository(context: Context): MainRepository {
        return MainRepository(tmdbService, provideMovieCache(context))
    }

}