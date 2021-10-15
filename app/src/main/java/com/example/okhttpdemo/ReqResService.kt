package com.example.okhttpdemo

import com.google.gson.JsonObject
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
    ): Call<JsonObject>
}