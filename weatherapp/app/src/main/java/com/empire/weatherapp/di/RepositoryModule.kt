package com.empire.weatherapp.di

import com.empire.weatherapp.data.repository.WeatherRepositoryImpl
import com.empire.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl) : WeatherRepository
}