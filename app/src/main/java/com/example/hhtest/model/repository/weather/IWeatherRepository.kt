package com.example.hhtest.model.repository.weather

import com.example.hhtest.model.entity.weather.ReadyWeather
import io.reactivex.Observable

interface IWeatherRepository {

    fun getWeatherByCoordinates(lat: Double, lon: Double, lang: String): Observable<ReadyWeather>
}