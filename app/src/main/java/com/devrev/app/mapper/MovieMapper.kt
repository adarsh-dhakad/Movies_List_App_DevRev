package com.devrev.app.mapper


import android.util.Log
import com.devrev.app.model.MovieUi
import com.devrev.network.di.data.MovieResponse

object MovieMapper{

    suspend fun mapDomainMovieToUi(
        domainMovie: MovieResponse
    ): MovieUi {
        Log.d("devKey" , "mapping")
        return MovieUi(
            id = domainMovie.id?:0,
            isAdultOnly = domainMovie.isAdultOnly?:false,
            popularity = domainMovie.popularity?:0.0,
            voteAverage = domainMovie.voteAverage?:0.0,
            voteCount = domainMovie.voteCount?:0,
            image = domainMovie.poster_path?:"",
            title = domainMovie.title?:"",
            overview = domainMovie.overview?:"",
            releaseDate = domainMovie.releaseDate?:"",
            originalLanguage = domainMovie.originalLanguage?:""
        )
    }

}