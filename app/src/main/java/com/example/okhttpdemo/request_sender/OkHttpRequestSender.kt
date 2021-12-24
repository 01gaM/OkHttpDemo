package com.example.okhttpdemo.request_sender

import android.util.Log
import com.example.okhttpdemo.data.ErrorResponse
import com.example.okhttpdemo.data.LoginResponse
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class OkHttpRequestSender(val toaster: Toaster) : RequestSender<Response> {
    companion object {
        private const val TAG = "OkHttpRequestSender"
    }

    private val gson = Gson()
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    override fun sendLoginRequest(email: String?, password: String?) {
        Log.i(TAG, "called sendLoginRequest from $TAG")
        val requestBodyBuilder = FormBody.Builder()
        if (email != null) {
            requestBodyBuilder.add("email", email)
        }

        if (password != null) {
            requestBodyBuilder.add("password", password)
        }

        val requestBody = requestBodyBuilder.build()

        val request: Request = Request.Builder()
            .url(RequestSender.LOGIN_URL)
            .post(requestBody)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                toaster.showOnFailureToast()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (it.isSuccessful) {
                        val token = getTokenFromResponse(it)
                        toaster.showTokenInToast(token)
                    } else {
                        val errorResponse =
                            gson.fromJson(it.body?.string(), ErrorResponse::class.java)
                        toaster.showErrorResponseToast(it.code, errorResponse)
                    }
                }
            }
        })
    }

    override fun getTokenFromResponse(response: Response): String {
        val jsonString = response.body?.string()
        return gson.fromJson(jsonString, LoginResponse::class.java).token
    }
}