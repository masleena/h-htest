package com.example.hhtest.dagger.weather

import com.example.hhtest.ui.signing.SigningActivity
import dagger.Subcomponent

@WeatherScope
@Subcomponent(modules = [WeatherModule::class])
interface WeatherComponent {

    fun inject(signingActivity: SigningActivity)

}