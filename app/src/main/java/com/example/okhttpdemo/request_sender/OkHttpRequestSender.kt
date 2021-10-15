package com.example.okhttpdemo.request_sender

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException

class OkHttpRequestSender(val toaster: Toaster) : RequestSender<Response> {
    companion object {
        private const val TAG = "OkHttpRequestSender"
    }

    private val gson = Gson()
    private val okHttpClient: OkHttpClient = OkHttpClient()

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
                if (response.isSuccessful) {
                    val token = getTokenFromResponse(response)
                    toaster.showTokenInToast(token)
                } else {
                    toaster.showErrorResponseToast(response.code)
                }
            }
        })
    }

    override fun getTokenFromResponse(response: Response): String {
        val jsonString = response.body?.string()
        return gson.fromJson(jsonString, JsonObject::class.java).get("token").asString
    }
}