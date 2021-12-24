package com.example.okhttpdemo.request_sender

import android.util.Log
import com.example.okhttpdemo.data.ErrorResponse
import com.example.okhttpdemo.data.LoginResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRequestSender(val toaster: Toaster) : RequestSender<Response<LoginResponse>> {
    companion object {
        private const val TAG = "RetrofitRequestSender"
    }

    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(RequestSender.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()
        )
        .build()

    private val service = retrofit.create(ReqResService::class.java)

    override fun sendLoginRequest(email: String?, password: String?) {
        Log.i(TAG, "called sendLoginRequest from $TAG")
        val call: Call<LoginResponse> = service.getToken(email, password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                Log.i(TAG, "called onResponse from $TAG")
                if (response.isSuccessful) {
                    val token = getTokenFromResponse(response)
                    toaster.showTokenInToast(token)
                } else {
                    val gson = Gson()
                    val errorResponse =
                        gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    toaster.showErrorResponseToast(response.code(), errorResponse)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.i(TAG, "called onFailure from $TAG")
                toaster.showOnFailureToast()
            }
        })
    }

    override fun getTokenFromResponse(response: Response<LoginResponse>): String {
        val responseBody = response.body()
        return responseBody?.token ?: ""
    }
}