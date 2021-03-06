package com.example.hhtest.model.repository.weather

import com.example.hhtest.api.API
import com.example.hhtest.dagger.weather.WeatherScope
import com.example.hhtest.util.API_KEY
import javax.inject.Inject

@WeatherScope
class WeatherRepositoryImpl @Inject constructor(val api: API) :
    IWeatherRepository {

    override fun getWeatherByCoordinates(lat: Double, lon: Double, lang: String) =
        api.getWeatherByCoordinates(API_KEY, lat, lon, UNITS_METRIC, lang)


    companion object {
        const val UNITS_METRIC = "metric"
    }
}