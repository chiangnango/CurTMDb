package com.example.curtmdb.architecture

import com.example.curtmdb.main.MainRepository
import com.example.curtmdb.main.MainViewModelFactory

object InjectorUtil {

    fun provideMainViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory(MainRepository())
    }
}