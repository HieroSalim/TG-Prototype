package com.example.domicilio.services.repository.remote

import com.example.domicilio.services.model.LoginModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @POST("user/Authentication")
    @FormUrlEncoded
    fun login(
        @Field("user") user : String,
        @Field("pass") pass : String,
        @Field("typeUser") typeUser : String
    ):Call<LoginModel>

}