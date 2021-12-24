package com.example.okhttpdemo.request_sender

import com.example.okhttpdemo.data.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ReqResService {
    @FormUrlEncoded
    @POST("api/login")
    fun getToken(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<LoginResponse>
}