package com.example.hhtest.ui.signing

import android.Manifest
import android.app.AlertDialog
import android.content.IntentFilter
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.hhtest.R
import com.example.hhtest.dagger.DaggerComponents
import com.example.hhtest.model.entity.weather.ReadyWeather
import com.example.hhtest.presenter.SigningPresenter
import com.example.hhtest.ui.base.BaseActivity
import com.example.hhtest.util.NetworkStateReceiver
import com.example.hhtest.util.Utils
import com.example.hhtest.util.afterTextChanged
import com.example.hhtest.util.moveToNextEditTextAfterClickEnter
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_signing.*
import javax.inject.Inject

class SigningActivity : BaseActivity(), ISignInView {

    @Inject
    lateinit var presenter: SigningPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signing)
        registerReceiver(NetworkStateReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
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

    override fun startGeoScanning() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED)
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.attention))
                    .setMessage(getString(R.string.geo_msg))
                    .setPositiveButton(getString(R.string.accept)) { dialog, _ ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_GEO)
                        }
                    }
                    .setNegativeButton(getString(R.string.exit)) { dialog, _ -> finish() }
                    .show()
            else checkLocationAndGetWeather()
        } else checkLocationAndGetWeather()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_GEO && grantResults[0] == 0) {
            checkLocationAndGetWeather()
        } else
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
                showMessage("Разрешите доступ к геолокации из настроек приложения")
    }

    private fun checkLocationAndGetWeather() {
        val latLon = Utils.getLocation(this)
        if (latLon != null) {
            presenter.onLocationReceive(latLon)
        } else
            showMessage(getString(R.string.err_geo_not_granted))
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

    override fun showErrorNotConnection() {
        showMessage("Превышено время ожидания")
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

    override fun showWeather(weatherData: ReadyWeather) {
        Observable.just(weatherData)
            .subscribeOn(Schedulers.io())
            .map { Utils.getImageFromNET(this, weatherData.icon) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val snackbar = Snackbar.make(
                    root,
                    getString(R.string.weather) + weatherData.type + "\n\n" + getString(R.string.humidity) + weatherData.humidity + "%"+ "\n\n" +getString(
                                            R.string.temp) + weatherData.temp + "℃" + "\n\n" + getString(R.string.wind) + Utils.getWindDirectionName(this, weatherData.windDeg) +
                            " ${weatherData.windSpeed} " + getString(R.string.meter_per_second),
                    Snackbar.LENGTH_INDEFINITE
                )
                it.setTint(ContextCompat.getColor(this, R.color.colorPrimary))
                snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    .apply {
                        maxLines = 8
                        setCompoundDrawablesWithIntrinsicBounds(it, null, null, null)
                        compoundDrawablePadding = 16
                    }
                snackbar.setAction("X") { snackbar.dismiss() }
                (snackbar.view.findViewById<View>(com.google.android.material.R.id.snackbar_action).layoutParams as LinearLayout.LayoutParams).gravity = Gravity.TOP
                snackbar.show()
            }.addToDisposables()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroy()
    }

    companion object {
        const val REQUEST_GEO = 5
    }
}
