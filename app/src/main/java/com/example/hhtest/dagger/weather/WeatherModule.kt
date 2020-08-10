package com.example.hhtest.dagger.weather

import com.example.hhtest.api.API
import com.example.hhtest.model.repository.user.IUserRepository
import com.example.hhtest.model.repository.weather.IWeatherRepository
import com.example.hhtest.model.repository.weather.WeatherRepositoryImpl
import com.example.hhtest.presenter.SigningPresenter
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {

    @Provides
    @WeatherScope
    fun provideWeatherRep(api: API): IWeatherRepository = WeatherRepositoryImpl(api)

    @Provides
    @WeatherScope
    fun provideSigningPresenter(
        userRep: IUserRepository,
        weatherRep: IWeatherRepository
    ) = SigningPresenter(userRep, weatherRep)

}