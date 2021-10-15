package com.example.okhttpdemo.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.okhttpdemo.MainActivity
import com.example.okhttpdemo.R
import com.example.okhttpdemo.request_sender.Toaster
import com.example.okhttpdemo.request_sender.OkHttpRequestSender
import com.example.okhttpdemo.request_sender.RetrofitRequestSender

class SendRequestFragment : Fragment(R.layout.fragment_request_menu), Toaster {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSuccess = view.findViewById<Button>(R.id.button_success)
        val btnFail = view.findViewById<Button>(R.id.button_fail)

        val sharedPreferences = context?.getSharedPreferences(
            SelectLibraryFragment.APP_PREFERENCES, Context.MODE_PRIVATE
        )

        val isRetrofit = sharedPreferences?.getBoolean(
            SelectLibraryFragment.APP_PREFERENCES_IS_RETROFIT_SELECTED, false
        )
        val requestSender =
            if (isRetrofit == true)
                RetrofitRequestSender(this)
            else
                OkHttpRequestSender(this)


        btnSuccess.setOnClickListener {
            requestSender.sendLoginRequest(
                "eve.holt@reqres.in",
                "cityslicka"
            )
        }

        btnFail.setOnClickListener {
            requestSender.sendLoginRequest(
                "peter@klaven",
                null
            )
        }
    }

    override fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun showTokenInToast(token: String) {
        Log.i(MainActivity.TAG, "Received token: $token")
        showToast("Success!\nReceived token: $token")
    }

    override fun showOnFailureToast() {
        val message = "No response from server!"
        Log.e(MainActivity.TAG, message)
        showToast(message)
    }

    override fun showErrorResponseToast(responseCode: Int) {
        showToast("Bad request!")
        Log.e(MainActivity.TAG, "Error! Response code: $responseCode")
    }
}