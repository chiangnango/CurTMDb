package com.example.curtmdb.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curtmdb.data.Movie
import com.example.curtmdb.util.APIUtil
import com.example.curtmdb.util.ImageUtil.imageConfig
import com.example.curtmdb.util.SingleLiveEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    private val _showSpinner = SingleLiveEvent<Boolean>()
    val showSpinner: LiveData<Boolean> = _showSpinner

    fun fetchData() {
        showSpinner(true)
        viewModelScope.launch {
            val configDeferred = async { repository.fetchTMDbConfiguration() }
            val popularDeferred = async { repository.fetchPopularMovies() }

            when (val configResult = configDeferred.await()) {
                is APIUtil.APIResult.Success -> imageConfig = configResult.data.imageConfig
                else -> Unit    // TODO: error handling
            }

            when (val popular = popularDeferred.await()) {
                is APIUtil.APIResult.Success -> _popularMovies.value = popular.data.movies
                else -> Unit // TODO: error handling
            }

            showSpinner(false)
        }
    }

    private fun showSpinner(show: Boolean) {
        _showSpinner.value = show
    }
}