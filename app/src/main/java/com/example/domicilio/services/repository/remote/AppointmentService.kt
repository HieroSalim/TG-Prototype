package com.example.domicilio.services.repository.remote

import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.model.ObjectModel
import retrofit2.Call
import retrofit2.http.*

interface AppointmentService {

    @POST("/appointment")
    @FormUrlEncoded
    fun create(
        @Header("Authorization") token: String,
        @Field("user") user: String,
        @Field("description") description: String,
        @Field("dateHour") dateHour: String,
        @Field("doctors") doctors: String,
        @Field("statusDoctor") statussDoctor: Int,
        @Field("fkAddress") fkAddress: Int
    ):Call<MessageModel>

    @GET("/appointment/{user}")
    fun loadWait(
        @Header("Authorization") token: String,
        @Path("user") user: String
    ):Call<ObjectModel>

    @GET("/appointment/confirm/{user}")
    fun loadAccepts(
        @Header("Authorization") token: String,
        @Path("user") user: String
    ):Call<ObjectModel>

    @GET("/appointment/completes/{user}")
    fun loadCompletes(
        @Header("Authorization") token: String,
        @Path("user") user: String
    ):Call<ObjectModel>
}