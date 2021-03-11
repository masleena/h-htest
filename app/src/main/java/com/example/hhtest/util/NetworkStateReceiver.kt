package com.example.hhtest.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.NetworkInfo.DetailedState.*
import com.example.hhtest.R

class NetworkStateReceiver : BroadcastReceiver() {

    var isFirstRun = true

    override fun onReceive(context: Context?, intent: Intent?) {
        if (checkIsFirstRun()) return
        val networkState = intent?.extras?.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo?
        if (context != null && networkState != null)
            checkNetworkState(context, networkState)
    }

    private fun checkNetworkState(context: Context, networkState: NetworkInfo) {
        when (networkState.detailedState) {
            CONNECTED -> context.showToastMessage(context.getString(R.string.network_connected))
            CONNECTING -> context.showToastMessage(context.getString(R.string.network_conntecting))
            else -> context.showToastMessage(context.getString(R.string.network_error))
        }
    }

    private fun checkIsFirstRun() : Boolean {
        if (isFirstRun) {
            isFirstRun = false
            return true
        }
        return isFirstRun
    }

}