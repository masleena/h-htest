package com.example.hhtest.dagger.weather

import com.example.hhtest.api.API
import com.example.hhtest.model.repository.weather.IWeatherRepository
import com.example.hhtest.model.repository.weather.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {

    @Provides
    @WeatherScope
    fun provideWeatherRepository(api: API) : IWeatherRepository = WeatherRepositoryImpl(api)
}