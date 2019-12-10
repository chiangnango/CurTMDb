package com.example.curtmdb.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.curtmdb.CoroutinesTestRule
import com.example.curtmdb.data.APIConfig
import com.example.curtmdb.util.APIUtil
import com.example.curtmdb.util.ImageUtil
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var mockRepository: MainRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = MainViewModel(mockRepository)
    }

    @Test
    fun fetchData_firstFetchConfig_thenFetchPopularMovies() = coroutinesTestRule.testDispatcher.runBlockingTest {
        assertThat(ImageUtil.isImageConfigReady()).isFalse()

        val apiConfig = mockk<APIConfig>(relaxed = true)
        val successResult = APIUtil.APIResult.Success(apiConfig)
        coEvery { mockRepository.fetchTMDbConfiguration() } returns successResult

        viewModel.fetchData()

        coVerifySequence {
            mockRepository.fetchTMDbConfiguration()
            mockRepository.fetchPopularMovies(viewModel.viewModelScope)
        }
    }

    @Test
    fun activityRecreate_fetchDataAgain_wontFetchConfig_wontCreateLivePagedList() {
        assertThat(ImageUtil.isImageConfigReady()).isFalse()
        val apiConfig = mockk<APIConfig>(relaxed = true)
        val successResult = APIUtil.APIResult.Success(apiConfig)
        coEvery { mockRepository.fetchTMDbConfiguration() } returns successResult

        viewModel.fetchData()

        coVerifySequence {
            mockRepository.fetchTMDbConfiguration()
            mockRepository.fetchPopularMovies(viewModel.viewModelScope)
        }

        assertThat(ImageUtil.isImageConfigReady()).isTrue()

        viewModel.fetchData()

        confirmVerified()
    }

}