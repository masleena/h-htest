package com.example.hhtest.util

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.example.hhtest.R
import java.util.*

object Utils {

    fun getSystemLanguage() = Locale.getDefault().language

    fun getWindDirectionName(context: Context,direction: Float) : String {
        if (direction>337.5) return context.resources.getString(R.string.wind_n)
        if (direction>292.5) return context.resources.getString(R.string.wind_nw)
        if (direction>247.5) return context.resources.getString(R.string.wind_w)
        if (direction>202.5) return context.resources.getString(R.string.wind_sw)
        if (direction>157.5) return context.resources.getString(R.string.wind_s)
        if (direction>122.5) return context.resources.getString(R.string.wind_se)
        if (direction>67.5) return context.resources.getString(R.string.wind_e)
        if (direction>22.5) return context.resources.getString(R.string.wind_ne)
        return context.resources.getString(R.string.wind_n)
    }

    fun getImageFromNET(context: Context, icon: String) : Drawable {
        return Glide.with(context).load("${BASE_IMAGE_URL}${icon}${BASE_IMAGE_URL_FINAL_PATH}").submit().get()
    }
}