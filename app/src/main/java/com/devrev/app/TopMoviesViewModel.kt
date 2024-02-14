package com.devrev.app

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.devrev.app.mapper.MovieMapper
import com.devrev.app.model.MovieUi
import com.devrev.network.di.data.MovieResponse
import com.devrev.network.di.repository.MoviesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TopMoviesViewModel(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
) : ViewModel() {

    suspend fun getTopMovies(): Flow<PagingData<MovieUi>> {
        return moviesRemoteDataSource.getMovies().map { pagingData ->
            pagingData.map {
                MovieMapper.mapDomainMovieToUi(domainMovie = it)
            }
        }.cachedIn(viewModelScope)
    }
}