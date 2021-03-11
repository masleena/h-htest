package com.example.hhtest.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import javax.inject.Inject

class LocationListener @Inject constructor(val context: Context) : LocationListener {

    private lateinit var locationManager: LocationManager
    lateinit var locationCallback: (LatLng) -> Unit
    lateinit var receivedLocation: LatLng

    fun prepare(locationCallback: (LatLng) -> Unit) {
        this.locationCallback = locationCallback
    }

    override fun onLocationChanged(location: Location) {
        locationManager.removeUpdates(this)
        val latLng = LatLng(location.latitude, location.longitude)
        receivedLocation = latLng
        locationCallback.invoke(latLng)
    }

    override fun onProviderDisabled(provider: String?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    @SuppressLint("MissingPermission")
    fun startReceivingLocation() {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this)
    }

    companion object {
        class LatLng(val lat: Double, val lon: Double)
        const val MIN_TIME: Long = 200
        const val MIN_DISTANCE = 100f
    }
}