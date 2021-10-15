package com.example.okhttpdemo.request_sender


interface Toaster {
    fun showToast(message: String)

    fun showTokenInToast(token: String)

    fun showOnFailureToast()

    fun showErrorResponseToast(responseCode: Int)
}