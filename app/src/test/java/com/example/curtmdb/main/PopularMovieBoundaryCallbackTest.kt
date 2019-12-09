package com.example.curtmdb.main

import com.example.curtmdb.CoroutinesTestRule
import com.example.curtmdb.api.TMDbService
import com.example.curtmdb.data.Movie
import com.example.curtmdb.data.PopularResult
import com.example.curtmdb.db.MovieLocalCache
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.mock.Calls

@ExperimentalCoroutinesApi
class PopularMovieBoundaryCallbackTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var mockService: TMDbService

    @MockK
    private lateinit var mockCache: MovieLocalCache

    private lateinit var callback: PopularMovieBoundaryCallback


    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        callback = PopularMovieBoundaryCallback(GlobalScope, mockService, mockCache)
    }

    @Test
    fun onZeroItemsLoaded_fetchDataWithPage1() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val movieList = mutableListOf<Movie>().apply {
            repeat(2) {
                add(mockk(relaxed = true))
            }
        }
        val popularResult: PopularResult = mockk(relaxed = true)

        val slot = slot<List<Movie>>()
        every { popularResult.movies } returns movieList
        every { mockService.popularMovies(any()) } returns Calls.response(popularResult)
        coEvery { mockCache.insert(capture(slot)) } just Runs

        callback.onZeroItemsLoaded()

        coVerify {
            mockService.popularMovies(1)
        }

        coVerify {
            mockCache.insert(any())
        }

        assertThat(slot.captured).hasSize(2)
    }
}