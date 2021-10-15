package com.example.okhttpdemo


interface Toaster {
    fun showToast(message: String)

    fun showTokenInToast(token: String)

    fun showOnFailureToast()

    fun showErrorResponseToast(responseCode: Int)
}