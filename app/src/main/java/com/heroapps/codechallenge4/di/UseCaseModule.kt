package com.heroapps.codechallenge4.di

import com.heroapps.codechallenge4.data.repository.MoviesRepository
import com.heroapps.codechallenge4.domain.use_case.GetMovieByIdUseCase
import com.heroapps.codechallenge4.domain.use_case.GetMoviesUseCase
import com.heroapps.codechallenge4.domain.use_case.ToggleWatchlistUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(repository: MoviesRepository) = GetMoviesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMovieByIdUseCase(repository: MoviesRepository) = GetMovieByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideToggleWatchlistUseCase(repository: MoviesRepository) = ToggleWatchlistUseCase(repository)
}