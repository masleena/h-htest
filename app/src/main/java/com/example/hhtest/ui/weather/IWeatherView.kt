package com.example.hhtest.ui.weather

import com.example.hhtest.model.entity.weather.ReadyWeather
import com.example.hhtest.ui.base.IBaseView

interface IWeatherView : IBaseView {

    fun showWeather(weatherData: ReadyWeather)

    fun showErrorNotConnection()

    fun showCustomError(msg: String)

    fun terminateLoadingStatus()
}