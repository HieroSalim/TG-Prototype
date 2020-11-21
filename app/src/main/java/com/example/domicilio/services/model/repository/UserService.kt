package com.example.domicilio.services.model.repository

import okhttp3.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.example.domicilio.services.model.Login

interface UserService {

    @POST("Authentication/Login")
    @FormUrlEncoded
    fun login(
        @Field("user") user : String,
        @Field("pass") pass : String,
        @Field("typeUser") typeUser : String
    )

}