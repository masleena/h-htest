package com.example.hhtest.domain.interactor.auth

import com.example.hhtest.dagger.auth.AuthScope
import com.example.hhtest.domain.usecase.auth.SignInUseCase
import com.example.hhtest.domain.usecase.auth.SignUpUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AuthScope
class AuthInteractor @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) {

    fun signUp(email: String, password: String) = signInUseCase.execute(email, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun signIn(email: String, password: String) = signUpUseCase.execute(email, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}