package com.example.okhttpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private val okHttpClient: OkHttpClient = OkHttpClient()
    private val gson: Gson = Gson()

    private lateinit var btnSuccess: Button
    private lateinit var btnFail: Button

    companion object {
        const val TAG = "MAIN_ACTIVITY"
        const val URL = "https://reqres.in/api/login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSuccess = findViewById(R.id.button_success)
        btnSuccess.setOnClickListener { sendLoginRequest("eve.holt@reqres.in", "cityslicka") }

        btnFail = findViewById(R.id.button_fail)
        btnFail.setOnClickListener { sendLoginRequest("peter@klaven", null) }
    }

    private fun sendLoginRequest(email: String?, password: String?) {
        val requestBodyBuilder = FormBody.Builder()
        if (email != null) {
            requestBodyBuilder.add("email", email)
        }

        if (password != null) {
            requestBodyBuilder.add("password", password)
        }

        val requestBody = requestBodyBuilder.build()

        val request: Request = Request.Builder()
            .url(URL)
            .post(requestBody)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "No response from server!")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.i(TAG, "Response is successful")
                    val token = getTokenFromResponse(response)
                    showToast("Success!\nReceived token: $token")
                    Log.i(TAG, "Received token: $token")

                } else {
                    showToast("Bad request!")
                    Log.e(TAG, "Error! Response code: ${response.code}")
                }
            }
        })
    }

    fun getTokenFromResponse(response: Response): String {
        val jsonString = response.body?.string()
        return gson.fromJson(jsonString, JsonObject::class.java).get("token").asString
    }

    fun showToast(message: String) {
        runOnUiThread { Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show() }
    }
}