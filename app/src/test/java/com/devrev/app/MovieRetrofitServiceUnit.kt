package com.devrev.app


import com.devrev.app.data.MoviesData
import com.devrev.network.api.MovieService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieRetrofitServiceUnit {

    private val mockWebServer = MockWebServer()
    private lateinit var movieService: MovieService

    @Before
    fun setUp() {
        mockWebServer.start()
        mockWebServer.dispatcher = setUpMockWebServerDispatcher()
        setUpMovieRetrofitService()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Assert get latest movies remote response structure match JSON Server response`() = runBlocking {
        val movies = movieService.getLatestMovies(
            language = "en-US",
            page = 1
        )


        assertEquals(
            "Latest movies size does not match.",
            MoviesData.provideRemoteMoviesFromAssets().size,
            movies.body()?.results?.size ?: 0
        )
    }

    @Test
    fun `Assert get popular movies remote response structure match JSON Server response`() = runBlocking {
        val movies = movieService.getPopularMovies(
            language = "en-US",
            page = 1
        )


        assertEquals(
            "Popular movies size does not match.",
            MoviesData.provideRemoteMoviesFromAssets().size,
            movies.body()?.results?.size ?: 0
        )
    }

    private fun setUpMovieRetrofitService() {
        movieService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(MovieService::class.java)
    }

    private fun setUpMockWebServerDispatcher(): Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            println("BASE_URL${request.path}")
            return when (request.path) {
                "/movie/now_playing?language=en-US&page=1" -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(FileReaderUtil.kotlinReadFileWithNewLineFromResources("movies.json"))
                }
                "/movie/popular?language=en-US&page=1" -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(FileReaderUtil.kotlinReadFileWithNewLineFromResources("movies.json"))
                }
                else -> MockResponse().setResponseCode(404)
            }
        }
    }
}