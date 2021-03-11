package com.example.hhtest.presenter

import androidx.room.EmptyResultSetException
import com.example.hhtest.domain.interactor.auth.AuthInteractor
import com.example.hhtest.ui.signing.ISignInView
import com.example.hhtest.util.EMAIL_CONDITION
import com.example.hhtest.util.PASSWORD_CONDITION
import javax.inject.Inject


class SigningPresenter @Inject constructor(
    val authInteractor: AuthInteractor
) : BasePresenter<ISignInView>() {

    fun onClickSignIn(email: String, password: String) {
        authInteractor.signIn(email, password)
            .doOnSubscribe { _view.showProgressAndLockButton() }
            .subscribe({
                _view.prepeareForShowingWeather()
            }, {
                if (it is EmptyResultSetException)
                    _view.showErrorUserNotFound()
            }).addToDisposables()
    }

    fun onClickSignUp(email: String, password: String) {
        if (isSignInDataValid(email, password))
            authInteractor.signUp(email, password)
                .doOnSubscribe { _view.showProgressAndLockButton() }
                .subscribe { isUserSigned ->
                    if (isUserSigned)
                        _view.prepeareForShowingWeather()
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

    private fun checkEmail(email: String) = email.matches(Regex(EMAIL_CONDITION))

    private fun checkPassword(password: String) = password.matches(Regex(PASSWORD_CONDITION))
}