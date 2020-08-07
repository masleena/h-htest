package com.example.hhtest.model.repository.weather

import com.example.hhtest.model.entity.weather.WeatherResponse
import io.reactivex.Single
import retrofit2.Response

interface IWeatherRepository {

    fun getWeatherByCity(cityName: String, units: String, lang: String): Single<Response<WeatherResponse>>

    fun getWeatherByCoordinates(lat: Double, lon: Double, units: String,
    lang: String): Single<Response<WeatherResponse>>
}