package com.example.curtmdb

import android.app.Application
import com.example.curtmdb.di.AppComponent
import com.example.curtmdb.di.DaggerAppComponent

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(this)
    }
}