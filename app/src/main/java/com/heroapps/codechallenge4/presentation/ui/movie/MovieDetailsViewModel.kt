package com.heroapps.codechallenge4.presentation.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.heroapps.codechallenge4.common.Response
import com.heroapps.codechallenge4.common.base.BaseViewModel
import com.heroapps.codechallenge4.data.source.remote.response.MovieItemResponse
import com.heroapps.codechallenge4.domain.use_case.GetMovieByIdUseCase
import com.heroapps.codechallenge4.domain.use_case.ToggleWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase
) : BaseViewModel() {

    private val movieId = savedStateHandle.getStateFlow("id", 0)

    private val _movieDetails = MutableStateFlow(MovieItemResponse())
    val movieDetails = _movieDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        if (movieId.value != 0) {
            Timber.d(movieId.value.toString())
            getMovieDetails()
        } else {
            Timber.d(movieId.value.toString())
        }
    }

    private fun getMovieDetails() {
        getMovieByIdUseCase(movieId.value).onEach { result ->
            when (result) {
                is Response.Success -> {
                    _movieDetails.value = result.data ?: MovieItemResponse()
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

    fun toggleWatchlist() {
        toggleWatchlistUseCase(movieId.value).onEach { result ->
            when (result) {
                is Response.Success -> {
                    _movieDetails.value = result.data ?: MovieItemResponse()
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
}