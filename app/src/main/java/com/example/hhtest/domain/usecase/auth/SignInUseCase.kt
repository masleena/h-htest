package com.example.hhtest.domain.usecase.auth

import com.example.hhtest.dagger.auth.AuthScope
import com.example.hhtest.model.repository.user.IUserRepository
import javax.inject.Inject

@AuthScope
class SignInUseCase @Inject constructor(private val rep: IUserRepository) {

    fun execute(email: String, password: String) = rep.signIn(email, password)
}