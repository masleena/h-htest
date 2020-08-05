package com.example.hhtest.repository.weather

import com.example.hhtest.api.API
import com.example.hhtest.util.API_KEY
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(val api: API) : IWeatherRepository {

    override fun getWeatherByCity(cityName: String, units: String, lang: String) =
        api.getWeatherByCityName(API_KEY, cityName, units, lang)

    override fun getWeatherByCoordinates(lat: Double, lon: Double, units: String, lang: String) =
        api.getWeatherByCoordinates(API_KEY, lat, lon, units, lang)
}