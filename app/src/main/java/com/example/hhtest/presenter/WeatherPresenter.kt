package com.example.hhtest.presenter

import com.example.hhtest.dagger.weather.WeatherScope
import com.example.hhtest.domain.interactor.weather.WeatherInteractor
import com.example.hhtest.ui.weather.IWeatherView
import com.example.hhtest.util.Utils
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import javax.inject.Inject

@WeatherScope
class WeatherPresenter @Inject constructor(
    val weatherInteractor: WeatherInteractor
) : BasePresenter<IWeatherView>() {

    fun onLocationReceive(latLon: Pair<Double, Double>) {
        weatherInteractor.getWeatherByCoordinates(latLon.first, latLon.second, Utils.getSystemLanguage())
            .doOnTerminate { _view.terminateLoadingStatus() }
            .subscribe({
                _view.showWeather(it)
            }) {
                if (it is SocketTimeoutException || it is InterruptedIOException)
                    _view.showErrorNotConnection()
                if (it is WeatherInteractor.GetWeatherException)
                    it.errorBody?.let { errorBody -> _view.showCustomError(errorBody.message) }
            }.addToDisposables()
    }
}