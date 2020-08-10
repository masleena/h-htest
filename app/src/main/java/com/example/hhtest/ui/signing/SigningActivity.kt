package com.example.hhtest.ui.signing

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.example.hhtest.R
import com.example.hhtest.dagger.DaggerComponents
import com.example.hhtest.model.entity.weather.Weather
import com.example.hhtest.presenter.SigningPresenter
import com.example.hhtest.ui.base.BaseActivity
import com.example.hhtest.util.afterTextChanged
import com.example.hhtest.util.moveToNextEditTextAfterClickEnter
import kotlinx.android.synthetic.main.activity_signing.*
import javax.inject.Inject

class SigningActivity : BaseActivity(), ISignInView {

    @Inject
    lateinit var presenter: SigningPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signing)
        DaggerComponents.weatherComponent.inject(this)
        presenter.onCreateView(this)
        piv.addConditions(resources.getStringArray(R.array.conditions))
        initListeners()
    }

    private fun initListeners() {
        tvSignin.setOnClickListener { clickSignIn() }
        tvSignup.setOnClickListener { clickSignUp() }
        ivPasswordHint.setOnClickListener { showPasswordHint() }
        etEmail.moveToNextEditTextAfterClickEnter(etPassword)
        signInAfterClickEnterInPassword()
        isSignUpButtonShowing()
    }

    private fun clickSignIn() {
        hideKeyboard()
        presenter.onClickSignIn(etEmail.text.toString(), etPassword.text.toString())
    }

    private fun clickSignUp() {
        hideKeyboard()
        presenter.onClickSignUp(etEmail.text.toString(), etPassword.text.toString())
    }

    private fun isSignUpButtonShowing() {
        etEmail.afterTextChanged {
            if (it.isNotEmpty())
                tvSignup.visibility = View.VISIBLE
            else
                tvSignup.visibility = View.INVISIBLE
        }
    }

    private fun signInAfterClickEnterInPassword() {
        etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                clickSignIn()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun showErrorEmailIsIncorrect() {
        showMessage(getString(R.string.err_email_is_incorrect))
    }

    override fun showPasswordHint() {
        piv.show()
    }

    override fun showErrorUserNotFound() {
        showMessage(getString(R.string.err_user_not_found))
    }

    override fun showErrorUserIsExist() {
        showMessage(getString(R.string.err_user_is_exist))
    }

    override fun showWeather(weatherData: Weather?) {
        showMessage("Погода")
    }
}
