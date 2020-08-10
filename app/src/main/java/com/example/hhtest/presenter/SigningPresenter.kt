package com.example.hhtest.presenter

import androidx.room.EmptyResultSetException
import com.example.hhtest.model.repository.user.IUserRepository
import com.example.hhtest.model.repository.weather.IWeatherRepository
import com.example.hhtest.ui.signing.ISignInView
import com.example.hhtest.util.EMAIL_CONDITION
import com.example.hhtest.util.PASSWORD_CONDITION
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SigningPresenter constructor(
    val userRep: IUserRepository,
    val weatherRep: IWeatherRepository
) : BasePresenter<ISignInView>() {

    fun onClickSignIn(email: String, password: String) {
        userRep.signIn(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showWeather()
            }, {
                if (it is EmptyResultSetException)
                    _view.showErrorUserNotFound()
            }).addToDisposables()
    }

    fun onClickSignUp(email: String, password: String) {
        if (isSignInDataValid(email, password))
            userRep.signUp(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isUserSigned ->
                    if (isUserSigned)
                        showWeather()
                    else
                        _view.showErrorUserIsExist()
                }.addToDisposables()
    }

    private fun isSignInDataValid(email: String, password: String): Boolean {
        if (!checkEmail(email)) {
            _view.showErrorEmailIsIncorrect()
            return false
        }
        if (!checkPassword(password)) {
            _view.showPasswordHint()
            return false
        }
        return true
    }

    private fun showWeather() {
        _view.showWeather(null)
    }

    private fun checkEmail(email: String) = email.matches(Regex(EMAIL_CONDITION))

    private fun checkPassword(password: String) = password.matches(Regex(PASSWORD_CONDITION))
}