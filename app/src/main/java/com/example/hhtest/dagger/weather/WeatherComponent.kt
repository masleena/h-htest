package com.example.hhtest.dagger.weather

import dagger.Subcomponent

@WeatherScope
@Subcomponent(modules = [WeatherModule::class])
interface WeatherComponent {

}