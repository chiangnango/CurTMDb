package com.example.curtmdb.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.curtmdb.db.ViewModelKey
import com.example.curtmdb.main.MainViewModel
import com.example.curtmdb.util.MyViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindMyViewModelFactory(viewModelFactory: MyViewModelFactory): ViewModelProvider.Factory

}