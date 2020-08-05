package com.example.hhtest.repository.weather

import com.example.hhtest.entity.weather.WeatherResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface IWeatherRepository {

    fun getWeatherByCity(cityName: String, units: String, lang: String): Single<Response<WeatherResponse>>

    fun getWeatherByCoordinates(lat: Double, lon: Double, units: String,
    lang: String): Single<Response<WeatherResponse>>
}