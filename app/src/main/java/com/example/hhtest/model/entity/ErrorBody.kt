package com.example.hhtest.model.entity

import com.google.gson.annotations.SerializedName

class ErrorBody(
    @SerializedName("code")
    val code: Int,
    val message: String
)