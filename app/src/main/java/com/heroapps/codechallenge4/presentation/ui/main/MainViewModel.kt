package com.heroapps.codechallenge4.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.heroapps.codechallenge4.common.Response
import com.heroapps.codechallenge4.common.base.BaseViewModel
import com.heroapps.codechallenge4.data.source.remote.response.MoviesResponse
import com.heroapps.codechallenge4.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : BaseViewModel() {
    private val _movieList = MutableStateFlow(MoviesResponse())
    val movieList = _movieList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
        getMoviesUseCase().onEach { result ->
            when (result) {
                is Response.Success -> {
                    _movieList.value = result.data ?: MoviesResponse()
                    _isLoading.value = false
                }
                is Response.Error -> {
                    _isLoading.value = false
                }
                is Response.Loading -> {
                    _isLoading.value = true
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sortMoviesByTitle(): MoviesResponse {
        val newMovieList = _movieList.value.items.sortedBy { it.title }
        return MoviesResponse(newMovieList)
    }

    fun sortMoviesByDate(): MoviesResponse {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val newMovieList = _movieList.value.items.sortedBy { dateFormat.parse(it.releaseDate) }
        return MoviesResponse(newMovieList)
    }
}