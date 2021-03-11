package com.example.hhtest.domain.usecase.weather

import com.example.hhtest.dagger.weather.WeatherScope
import com.example.hhtest.model.repository.weather.IWeatherRepository
import java.lang.RuntimeException
import javax.inject.Inject

@WeatherScope
class GetWeatherByCoordinatesUseCase @Inject constructor(
    private val rep: IWeatherRepository
) {

    fun execute(lat: Double, lon: Double, lang: String) =
        rep.getWeatherByCoordinates(lat, lon, lang)

}