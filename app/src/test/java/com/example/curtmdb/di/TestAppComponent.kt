package com.example.curtmdb.di

import android.content.Context
import com.example.curtmdb.api.TMDbServiceTest
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface TestAppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }

    @ExperimentalCoroutinesApi
    fun inject(test: TMDbServiceTest)
}