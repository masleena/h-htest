package com.example.hhtest.dagger.auth

import com.example.hhtest.ui.signing.SigningActivity
import dagger.Subcomponent

@AuthScope
@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {

    fun inject(signingActivity: SigningActivity)
}