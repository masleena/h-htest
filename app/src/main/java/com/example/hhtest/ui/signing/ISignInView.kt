package com.example.hhtest.ui.signing

import com.example.hhtest.ui.base.IBaseView

interface ISignInView: IBaseView {

    fun showErrorEmailIsIncorrect()

    fun showPasswordHint()

    fun showErrorUserNotFound()

    fun showErrorUserIsExist()

    fun prepeareForShowingWeather()

    fun showProgressAndLockButton()

    fun hideProgressUnlockButton()
}