package com.example.hhtest.api

import com.example.hhtest.model.entity.weather.WeatherResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("weather")
    fun getWeatherByCityName(@Query("appid") token: String,
                             @Query("q") cityName: String,
                             @Query("units") units: String,
                             @Query("lang") lang: String) : Single<Response<WeatherResponse>>

    @GET("weather")
    fun getWeatherByCoordinates(@Query("appid") token: String,
                                @Query("lat") lat: Double,
                                @Query("lon") lon: Double,
                                @Query("units") units: String,
                                @Query("lang") lang: String) : Single<Response<WeatherResponse>>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}