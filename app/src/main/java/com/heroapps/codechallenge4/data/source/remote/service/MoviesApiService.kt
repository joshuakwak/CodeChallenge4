package com.heroapps.codechallenge4.data.source.remote.service

import com.heroapps.codechallenge4.common.Constants.API_ENDPOINT
import com.heroapps.codechallenge4.common.Constants.METHOD_NAME
import com.heroapps.codechallenge4.data.source.remote.response.MovieItemResponse
import com.heroapps.codechallenge4.data.source.remote.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET(API_ENDPOINT)
    suspend fun getMovies(
        @Query(METHOD_NAME) methodName: String
    ): MoviesResponse

    @GET(API_ENDPOINT)
    suspend fun getMoviesById(
        @Query(METHOD_NAME) methodName: String,
        @Query("id") id: Int
    ): MovieItemResponse

    @GET(API_ENDPOINT)
    suspend fun toggleWatchlist(
        @Query(METHOD_NAME) methodName: String,
        @Query("id") id: Int
    ): MovieItemResponse
}