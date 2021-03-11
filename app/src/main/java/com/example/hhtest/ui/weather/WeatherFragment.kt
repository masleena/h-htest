package com.example.hhtest.ui.weather

import android.Manifest
import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.example.hhtest.R
import com.example.hhtest.dagger.DaggerComponents
import com.example.hhtest.model.entity.weather.ReadyWeather
import com.example.hhtest.presenter.WeatherPresenter
import com.example.hhtest.ui.base.BaseFragment
import com.example.hhtest.ui.signing.ISignInView
import com.example.hhtest.ui.signing.SigningActivity
import com.example.hhtest.util.LocationListener
import com.example.hhtest.util.Utils
import com.example.hhtest.util.showToastMessage
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherFragment : BaseFragment(R.layout.fragment_weather), IWeatherView {

    @Inject lateinit var presenter: WeatherPresenter
    @Inject lateinit var locationListener: LocationListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerComponents.weatherComponent.inject(this)
        presenter.onCreateView(this)
        startGeoScanning()
    }

    private fun checkLocationAndGetWeather() {
        locationListener.prepare { latlng ->
            presenter.onLocationReceive(latlng.lat to latlng.lon)
        }
        locationListener.startReceivingLocation()
    }

    override fun showWeather(weatherData: ReadyWeather) {
        Observable.just(weatherData)
            .subscribeOn(Schedulers.io())
            .map { Utils.getImageFromNET(requireContext(), weatherData.icon) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showSnackBarWithWeather(weatherData, it)
                (requireActivity() as ISignInView).hideProgressUnlockButton()
            }.addToDisposables()
    }

    private fun showSnackBarWithWeather(weatherData: ReadyWeather, weatherPic: Drawable) {
        val snackbar = makeSnackbar(weatherData)
        weatherPic.setTint(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        snackbar.prepeareSnackbar(weatherPic)
        snackbar.setAction("X") { snackbar.dismiss() }
        snackbar.setActionButtonToTop()
        snackbar.show()
    }

    private fun makeSnackbar(weatherData: ReadyWeather) = Snackbar.make(requireView(),
        getString(R.string.weather) + weatherData.type + "\n\n" + getString(R.string.humidity) + weatherData.humidity + "%"+ "\n\n" +getString(
            R.string.temp) + weatherData.temp + "℃" + "\n\n" + getString(R.string.wind) + Utils.getWindDirectionName(requireContext(), weatherData.windDeg) +
                " ${weatherData.windSpeed} " + getString(R.string.meter_per_second),
        Snackbar.LENGTH_INDEFINITE
    )

    private fun Snackbar.prepeareSnackbar(weatherPic: Drawable) = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
        maxLines = 8
        setCompoundDrawablesWithIntrinsicBounds(weatherPic, null, null, null)
        compoundDrawablePadding = 16
    }

    private fun Snackbar.setActionButtonToTop() {
        (view.findViewById<View>(com.google.android.material.R.id.snackbar_action).layoutParams as LinearLayout.LayoutParams).gravity = Gravity.TOP
    }

    fun startGeoScanning() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PermissionChecker.PERMISSION_GRANTED)
                createGeoDialog()
            else checkLocationAndGetWeather()
        } else checkLocationAndGetWeather()
    }

    private fun createGeoDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.attention))
            .setMessage(getString(R.string.geo_msg))
            .setPositiveButton(getString(R.string.accept)) { dialog, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        SigningActivity.REQUEST_GEO
                    )
                }
            }
            .setNegativeButton(getString(R.string.exit)) { dialog, _ -> requireActivity().finishAffinity() }
            .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SigningActivity.REQUEST_GEO && grantResults[0] == 0) {
            checkLocationAndGetWeather()
        } else
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
                (getString(R.string.request_geo_from_settings))
    }

    override fun showErrorNotConnection() {
        requireContext().showToastMessage(getString(R.string.error_not_network))
    }

    override fun showCustomError(msg: String) {
        requireContext().showToastMessage(msg)
    }

    override fun terminateLoadingStatus() {
        (requireActivity() as ISignInView).hideProgressUnlockButton()
    }

    companion object {
        val TAG = WeatherFragment::class.simpleName
    }
}