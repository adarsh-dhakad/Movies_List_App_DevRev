package com.devrev.app.data

import com.devrev.app.FileReaderUtil
import com.devrev.network.data.MovieResponse
import com.devrev.network.data.ResponseItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object MoviesData {

    private val gson = Gson()
    private val moviesResponseType: Type = object : TypeToken<ResponseItems<MovieResponse>>() {}.type

    fun provideRemoteMoviesFromAssets(): List<MovieResponse> {
        val jsonString = FileReaderUtil.kotlinReadFileWithNewLineFromResources("movies.json")
        val moviesResponse: ResponseItems<MovieResponse> = gson.fromJson(jsonString, moviesResponseType)
        return moviesResponse.results ?: emptyList()
    }
}