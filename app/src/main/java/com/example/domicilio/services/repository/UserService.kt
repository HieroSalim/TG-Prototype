package com.example.domicilio.services.repository

import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.model.UserModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @POST("authentication")
    @FormUrlEncoded
    fun login(
            @Field("login") login: String,
            @Field("pass") pass: String
    ): Call<LoginModel>

    @POST("user")
    @FormUrlEncoded
    fun register(
            @Field("CPF") CPF: String,
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("user") user: String,
            @Field("pass") pass: String,
            @Field("cell") cell: String,
            @Field("typeUser") typeUser: String
    ): Call<UserModel>

}