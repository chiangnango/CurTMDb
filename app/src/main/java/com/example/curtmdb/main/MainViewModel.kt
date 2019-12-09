package com.example.curtmdb.main

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.curtmdb.data.Movie
import com.example.curtmdb.util.APIUtil
import com.example.curtmdb.util.ImageUtil.imageConfig
import com.example.curtmdb.util.ImageUtil.isImageConfigReady
import com.example.curtmdb.util.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _showSpinner = SingleLiveEvent<Boolean>()
    val showSpinner: LiveData<Boolean> = _showSpinner

    private val popularResult = MutableLiveData<MainRepository.PopularMovieResult>()

    val popularMoviesResult: LiveData<PagedList<Movie>> = Transformations.switchMap(popularResult) {
        it.data
    }

    fun fetchData() {
        viewModelScope.launch {
            if (!isImageConfigReady()) {
                showSpinner(true)
                when (val configResult = repository.fetchTMDbConfiguration()) {
                    is APIUtil.APIResult.Success -> imageConfig = configResult.data.imageConfig
                    else -> Unit    // TODO: error handling
                }
                showSpinner(false)
            }

            popularResult.value = repository.fetchPopularMovies(viewModelScope)
        }
    }

    private fun showSpinner(show: Boolean) {
        _showSpinner.value = show
    }
}