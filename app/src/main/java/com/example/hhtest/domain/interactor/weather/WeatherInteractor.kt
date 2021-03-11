package com.example.hhtest.domain.interactor.weather

import com.example.hhtest.dagger.weather.WeatherScope
import com.example.hhtest.domain.usecase.weather.GetWeatherByCoordinatesUseCase
import com.example.hhtest.model.entity.ErrorBody
import com.example.hhtest.model.entity.weather.ReadyWeather
import com.example.hhtest.model.entity.weather.WeatherResponse
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.RuntimeException
import javax.inject.Inject

@WeatherScope
class WeatherInteractor @Inject constructor(
    private val getWeatherByCoordinatesUseCase: GetWeatherByCoordinatesUseCase
) {

    fun getWeatherByCoordinates(lat: Double, lon: Double, lang: String) =
        getWeatherByCoordinatesUseCase.execute(lat, lon, lang).map {
            if (it.isSuccessful && it.body() != null)
                prepareWeather(it.body()!!)
            else
                throw GetWeatherException(Gson().fromJson(it.errorBody()?.string(), ErrorBody::class.java))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

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

    class GetWeatherException(val errorBody: ErrorBody?) : RuntimeException()
}