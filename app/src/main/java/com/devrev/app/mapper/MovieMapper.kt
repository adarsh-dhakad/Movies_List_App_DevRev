package com.devrev.app.mapper


import android.util.Log
import com.devrev.app.model.MovieUi
import com.devrev.network.room.entity.LatestMovieEntity
import com.devrev.network.room.entity.PopularMovieEntity

object MovieMapper{

    suspend fun mapLatestMovieToUi(
        domainMovie: LatestMovieEntity
    ): MovieUi {
        return MovieUi(
            id = domainMovie.id?:0,
            image = domainMovie.poster_path?:"",
            title = domainMovie.title.toString(),
            createdAt = domainMovie.createdAt
        )
    }

    suspend fun mapPopularMovieToUi(
        domainMovie: PopularMovieEntity
    ): MovieUi {
        return MovieUi(
            id = domainMovie.id?:0,
            image = domainMovie.poster_path?:"",
            title = domainMovie.title?:"",
            createdAt = domainMovie.createdAt,
        )
    }

}