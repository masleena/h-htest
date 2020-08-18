package com.example.hhtest.model.repository.weather

import com.example.hhtest.api.API
import com.example.hhtest.model.entity.weather.ReadyWeather
import com.example.hhtest.model.entity.weather.WeatherResponse
import com.example.hhtest.util.API_KEY

class WeatherRepositoryImpl constructor(val api: API) :
    IWeatherRepository {

    override fun getWeatherByCoordinates(lat: Double, lon: Double, lang: String) =
        api.getWeatherByCoordinates(API_KEY, lat, lon, UNITS_METRIC, lang)
            .toObservable()
            .map { prepareWeather(it.body()!!) }

    private fun prepareWeather(weatherResponse: WeatherResponse) : ReadyWeather {
        return ReadyWeather(
            weatherResponse.rawWeather[0].id,
            weatherResponse.rawWeather[0].main,
            weatherResponse.rawWeather[0].icon,
            weatherResponse.rawMain.temp,
            weatherResponse.rawMain.humidity,
            weatherResponse.rawWind.speed,
            weatherResponse.rawWind.deg.toFloat()
        )
    }
    companion object {
        const val UNITS_METRIC = "metric"
    }
}