package com.heroapps.codechallenge4.domain.use_case

import com.heroapps.codechallenge4.common.Response
import com.heroapps.codechallenge4.data.repository.MoviesRepository
import com.heroapps.codechallenge4.data.source.remote.response.MovieItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ToggleWatchlistUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<Response<MovieItemResponse>> = flow {
        try {
            emit(Response.Loading())
            val data = repository.toggleWatchlist(id)
            emit(Response.Success(data))
        } catch (e: HttpException) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Response.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}