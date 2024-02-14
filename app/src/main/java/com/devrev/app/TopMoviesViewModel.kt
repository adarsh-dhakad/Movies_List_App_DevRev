package com.devrev.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.devrev.app.mapper.MovieMapper
import com.devrev.app.model.MovieUi
import com.devrev.network.data.MovieDetailsResponse
import com.devrev.network.data.ResponseMovieDetails
import com.devrev.network.repository.MoviesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TopMoviesViewModel(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
) : ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetailsResponse<ResponseMovieDetails>>()
    val movieDetails get() = _movieDetails

    suspend fun getLatestMovies(): Flow<PagingData<MovieUi>> {
        return moviesRemoteDataSource.getLatestMovies().map { pagingData ->
            pagingData.map {
                MovieMapper.mapLatestMovieToUi(domainMovie = it)
            }
        }.cachedIn(viewModelScope)
    }

    suspend fun getPopularMovies(): Flow<PagingData<MovieUi>> {
        return moviesRemoteDataSource.getPopularMovies().map { pagingData ->
            pagingData.map {
                MovieMapper.mapPopularMovieToUi(domainMovie = it)
            }
        }.cachedIn(viewModelScope)
    }

    fun getMovieDetails(movieId:Int){
         viewModelScope.launch {
             try {
                 val response = moviesRemoteDataSource.getMovieDetails(movieId)
                 if (response.isSuccessful) {
                     _movieDetails.value = MovieDetailsResponse.Success(response.body()!!)
                 } else {
                     _movieDetails.value = MovieDetailsResponse.Error(response.message())
                 }
             } catch (e: Exception) {
                 _movieDetails.value = MovieDetailsResponse.Error(e.localizedMessage ?: "An error occurred")
             }
         }
    }
}