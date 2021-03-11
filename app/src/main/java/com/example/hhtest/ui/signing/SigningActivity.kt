package com.example.hhtest.ui.signing

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import com.example.hhtest.R
import com.example.hhtest.dagger.DaggerComponents
import com.example.hhtest.presenter.SigningPresenter
import com.example.hhtest.ui.base.BaseActivity
import com.example.hhtest.ui.weather.WeatherFragment
import com.example.hhtest.util.NetworkStateReceiver
import com.example.hhtest.util.moveToNextEditTextAfterClickEnter
import kotlinx.android.synthetic.main.activity_signing.*
import javax.inject.Inject


class SigningActivity : BaseActivity(), ISignInView {

    @Inject lateinit var signingPresenter: SigningPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signing)
        registerReceiver(NetworkStateReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        DaggerComponents.authComponent.inject(this)
        signingPresenter.onCreateView(this)
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
        signingPresenter.onClickSignIn(etEmail.text.toString(), etPassword.text.toString())
    }

    private fun clickSignUp() {
        hideKeyboard()
        signingPresenter.onClickSignUp(etEmail.text.toString(), etPassword.text.toString())
    }

    override fun prepeareForShowingWeather() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, WeatherFragment(), WeatherFragment.TAG)
            .commitNow()
    }

    private fun isSignUpButtonShowing() {
        etEmail.doAfterTextChanged {
            if (it.toString().isNotEmpty())
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

    override fun showProgressAndLockButton() {
        progressBar.visibility = View.VISIBLE
        tvSignin.isEnabled = false
        tvSignup.isEnabled = false
    }

    override fun hideProgressUnlockButton() {
        progressBar.visibility = View.INVISIBLE
        tvSignin.isEnabled = true
        tvSignup.isEnabled = true
    }

    override fun onDestroy() {
        super.onDestroy()
        signingPresenter.onViewDestroy()
    }

    companion object {
        const val REQUEST_GEO = 5
    }
}
