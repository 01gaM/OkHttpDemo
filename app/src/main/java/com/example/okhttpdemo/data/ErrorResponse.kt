package com.example.okhttpdemo.data

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val message: String
)
