package com.example.hhtest.dagger.weather

import com.example.hhtest.ui.weather.WeatherFragment
import dagger.Subcomponent

@WeatherScope
@Subcomponent(modules = [WeatherModule::class])
interface WeatherComponent {

    fun inject(weatherFragment: WeatherFragment)
}