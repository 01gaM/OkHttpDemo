package com.example.okhttpdemo

interface RequestSender<T : Any> {
    companion object {
        const val BASE_URL = "https://reqres.in/"
        const val LOGIN_URL = BASE_URL + "api/login"
    }

    fun sendLoginRequest(email: String?, password: String?)
    fun getTokenFromResponse(response: T): String
}