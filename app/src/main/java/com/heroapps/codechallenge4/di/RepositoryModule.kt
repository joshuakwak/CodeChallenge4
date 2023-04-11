package com.heroapps.codechallenge4.di

import com.heroapps.codechallenge4.data.source.remote.service.MoviesApiService
import com.heroapps.codechallenge4.data.repository.MoviesRepository
import com.heroapps.codechallenge4.data.repository.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(api: MoviesApiService): MoviesRepository {
        return MoviesRepositoryImpl(api)
    }
}