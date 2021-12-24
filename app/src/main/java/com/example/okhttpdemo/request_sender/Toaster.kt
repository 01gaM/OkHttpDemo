package com.example.okhttpdemo.request_sender

import com.example.okhttpdemo.data.ErrorResponse


interface Toaster {
    fun showToast(message: String)

    fun showTokenInToast(token: String)

    fun showOnFailureToast()

    fun showErrorResponseToast(responseCode: Int, errorResponse: ErrorResponse)
}