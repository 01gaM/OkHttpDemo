package com.example.okhttpdemo

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRequestSender(val toaster: Toaster) : RequestSender<Response<JsonObject>> {
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(RequestSender.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(ReqResService::class.java)

    override fun sendLoginRequest(email: String?, password: String?) {
        val call: Call<JsonObject> = service.getToken(email, password)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.isSuccessful) {
                    val token = getTokenFromResponse(response)
                    toaster.showTokenInToast(token)
                } else {
                    toaster.showErrorResponseToast(response.code())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                toaster.showOnFailureToast()
            }
        })
    }

    override fun getTokenFromResponse(response: Response<JsonObject>): String {
        val responseBody = response.body()
        return responseBody?.get("token").toString()
    }
}