package com.example.hhtest.presenter

import androidx.room.EmptyResultSetException
import com.example.hhtest.model.repository.user.IUserRepository
import com.example.hhtest.model.repository.weather.IWeatherRepository
import com.example.hhtest.ui.signing.ISignInView
import com.example.hhtest.util.EMAIL_CONDITION
import com.example.hhtest.util.PASSWORD_CONDITION
import com.example.hhtest.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException

class SigningPresenter constructor(
    val userRep: IUserRepository,
    val weatherRep: IWeatherRepository
) : BasePresenter<ISignInView>() {

    fun onClickSignIn(email: String, password: String) {
        userRep.signIn(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _view.showProgressAndLockButton() }
            .doOnTerminate { _view.hideProgressUnlockButton() }
            .subscribe({
                _view.startGeoScanning()
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
                .doOnSubscribe { _view.showProgressAndLockButton() }
                .doOnTerminate { _view.hideProgressUnlockButton() }
                .subscribe { isUserSigned ->
                    if (isUserSigned)
                        _view.startGeoScanning()
                    else
                        _view.showErrorUserIsExist()
                }.addToDisposables()
    }

    fun onLocationReceive(latLon: Pair<Double, Double>) {
        weatherRep.getWeatherByCoordinates(latLon.first, latLon.second, Utils.getSystemLanguage())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _view.showWeather(it)
            }) {
                if (it is SocketTimeoutException)
                    _view.showErrorNotConnection()
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

    private fun checkEmail(email: String) = email.matches(Regex(EMAIL_CONDITION))

    private fun checkPassword(password: String) = password.matches(Regex(PASSWORD_CONDITION))
}