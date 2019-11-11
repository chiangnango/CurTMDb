package com.example.curtmdb.api

import com.example.curtmdb.util.APIUtil.createService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.await

@ExperimentalCoroutinesApi
class TMDbServiceTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var tmdbService: TMDbService

    @Before
    fun setup() {
        mockServer = MockWebServer()
        tmdbService = createService(url = mockServer.url("/").toString())
    }

    @Test
    fun configuration_normalResult() = runBlocking<Unit> {
        mockServer.enqueue(MockResponse().setBody(JSON_CONFIGURATION))
        val apiConfig = tmdbService.configuration().await()

        assertThat(apiConfig.imageConfig.baseUrl).isEqualTo("https://image.tmdb.org/t/p/")
        assertThat(apiConfig.imageConfig.posterSizes).hasSize(7)
        assertThat(apiConfig.imageConfig.posterSizes).containsExactly(
            "w92",
            "w154",
            "w185",
            "w342",
            "w500",
            "w780",
            "original"
        )
    }

    @Test
    fun moviePopular_normalResult() = runBlocking {
        mockServer.enqueue(MockResponse().setBody(JSON_MOVIE_POPULAR))
        val popular = tmdbService.popularMovies().await()

        assertThat(popular.totalPages).isEqualTo(1)
        assertThat(popular.totalResults).isEqualTo(2)
        assertThat(popular.page).isEqualTo(1)
        assertThat(popular.movies).hasSize(2)
        assertThat(popular.movies[0].title).isEqualTo("Joker")
        assertThat(popular.movies[1].title).isEqualTo("Terminator: Dark Fate")
    }


    @After
    fun teardown() {
        mockServer.shutdown()
    }

    companion object {
        private const val JSON_CONFIGURATION =
            "{\"images\":{\"base_url\":\"http://image.tmdb.org/t/p/\",\"secure_base_url\":\"https://image.tmdb.org/t/p/\",\"backdrop_sizes\":[\"w300\",\"w780\",\"w1280\",\"original\"],\"logo_sizes\":[\"w45\",\"w92\",\"w154\",\"w185\",\"w300\",\"w500\",\"original\"]," +
                    "\"poster_sizes\":[\"w92\",\"w154\",\"w185\",\"w342\",\"w500\",\"w780\",\"original\"],\"profile_sizes\":[\"w45\",\"w185\",\"h632\",\"original\"],\"still_sizes\":[\"w92\",\"w185\",\"w300\",\"original\"]},\"change_keys\":[\"adult\",\"air_date\",\"also_known_as\",\"alternative_titles\",\"biography\",\"birthday\",\"budget\",\"cast\",\"certifications\",\"character_names\",\"created_by\",\"crew\",\"deathday\",\"episode\",\"episode_number\",\"episode_run_time\",\"freebase_id\",\"freebase_mid\",\"general\",\"genres\",\"guest_stars\",\"homepage\",\"images\",\"imdb_id\",\"languages\",\"name\",\"network\",\"origin_country\",\"original_name\",\"original_title\",\"overview\",\"parts\",\"place_of_birth\",\"plot_keywords\",\"production_code\",\"production_companies\",\"production_countries\",\"releases\",\"revenue\",\"runtime\",\"season\",\"season_number\",\"season_regular\",\"spoken_languages\",\"status\",\"tagline\",\"title\",\"translations\",\"tvdb_id\",\"tvrage_id\",\"type\",\"video\",\"videos\"]}"

        private const val JSON_MOVIE_POPULAR = "{\"page\":1,\"total_results\":2,\"total_pages\":1,\"results\":[" +
                "{\"popularity\":639.314,\"vote_count\":5156,\"video\":false,\"poster_path\":\"\\/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg\",\"id\":475557,\"adult\":false,\"backdrop_path\":\"\\/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg\",\"original_language\":\"en\",\"original_title\":\"Joker\",\"genre_ids\":[80,18,53],\"title\":\"Joker\",\"vote_average\":8.5,\"overview\":\"During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.\",\"release_date\":\"2019-10-04\"}," +
                "{\"popularity\":341.055,\"vote_count\":458,\"video\":false,\"poster_path\":\"\\/vqzNJRH4YyquRiWxCCOH0aXggHI.jpg\",\"id\":290859,\"adult\":false,\"backdrop_path\":\"\\/rtf4vjjLZLalpOzDUi0Qd2GTUqq.jpg\",\"original_language\":\"en\",\"original_title\":\"Terminator: Dark Fate\",\"genre_ids\":[28,878],\"title\":\"Terminator: Dark Fate\",\"vote_average\":6.5,\"overview\":\"More than two decades have passed since Sarah Connor prevented Judgment Day, changed the future, and re-wrote the fate of the human race. Dani Ramos is living a simple life in Mexico City with her brother and father when a highly advanced and deadly new Terminator – a Rev-9 – travels back through time to hunt and kill her. Dani's survival depends on her joining forces with two warriors: Grace, an enhanced super-soldier from the future, and a battle-hardened Sarah Connor. As the Rev-9 ruthlessly destroys everything and everyone in its path on the hunt for Dani, the three are led to a T-800 from Sarah’s past that may be their last best hope.\",\"release_date\":\"2019-11-01\"}]}"
    }
}