package com.example.okhttpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity(), Toaster {
    companion object {
        const val TAG = "MAIN_ACTIVITY"
    }

    private lateinit var requestSender: RequestSender<*>

    private lateinit var btnSuccess: Button
    private lateinit var btnFail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isRetrofit = true
        requestSender =
            if (isRetrofit)
                RetrofitRequestSender(this)
            else
                OkHttpRequestSender(this)

        btnSuccess = findViewById(R.id.button_success)
        btnSuccess.setOnClickListener {
            requestSender.sendLoginRequest(
                "eve.holt@reqres.in",
                "cityslicka"
            )
        }

        btnFail = findViewById(R.id.button_fail)
        btnFail.setOnClickListener {
            requestSender.sendLoginRequest(
                "peter@klaven",
                null
            )
        }
    }

    override fun showToast(message: String) {
        runOnUiThread { Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show() }
    }

    override fun showTokenInToast(token: String) {
        Log.i(TAG, "Received token: $token")
        showToast("Success!\nReceived token: $token")
    }

    override fun showOnFailureToast() {
        val message = "No response from server!"
        Log.e(TAG, message)
        showToast(message)
    }

    override fun showErrorResponseToast(responseCode: Int) {
        showToast("Bad request!")
        Log.e(TAG, "Error! Response code: $responseCode")
    }
}