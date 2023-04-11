package com.heroapps.codechallenge4.data.repository

import com.heroapps.codechallenge4.common.base.BaseRepository
import com.heroapps.codechallenge4.data.source.remote.service.MoviesApiService
import com.heroapps.codechallenge4.BuildConfig
import com.heroapps.codechallenge4.common.Constants.METHOD_GET_MOVIES
import com.heroapps.codechallenge4.common.Constants.METHOD_GET_MOVIE_BY_ID
import com.heroapps.codechallenge4.common.Constants.METHOD_TOGGLE_WATCHLIST
import com.heroapps.codechallenge4.data.source.remote.response.MovieItemResponse
import com.heroapps.codechallenge4.data.source.remote.response.MoviesResponse

interface MoviesRepository {
    suspend fun getMovies(): MoviesResponse
    suspend fun getMoviesById(id: Int): MovieItemResponse
    suspend fun toggleWatchlist(id: Int): MovieItemResponse
}

class MoviesRepositoryImpl(
    private val apiService: MoviesApiService
) : BaseRepository(), MoviesRepository {

    override suspend fun getMovies(): MoviesResponse {
        return apiService.getMovies(METHOD_GET_MOVIES)
    }

    override suspend fun getMoviesById(id: Int): MovieItemResponse {
        return apiService.getMoviesById(METHOD_GET_MOVIE_BY_ID, id)
    }

    override suspend fun toggleWatchlist(id: Int): MovieItemResponse {
        return apiService.toggleWatchlist(METHOD_TOGGLE_WATCHLIST, id)
    }
}