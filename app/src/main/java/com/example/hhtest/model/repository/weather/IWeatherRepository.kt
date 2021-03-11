package com.example.hhtest.model.repository.weather

import com.example.hhtest.model.entity.weather.WeatherResponse
import io.reactivex.Single
import retrofit2.Response

interface IWeatherRepository {

    fun getWeatherByCoordinates(lat: Double, lon: Double, lang: String): Single<Response<WeatherResponse>>
}