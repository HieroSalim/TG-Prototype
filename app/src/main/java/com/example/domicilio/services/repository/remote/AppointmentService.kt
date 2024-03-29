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

    @GET("/appointment/solicitation/{user}")
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

    @POST("/appointment/setResponse")
    @FormUrlEncoded
    fun accept(
        @Header("Authorization") token: String,
        @Field("doctors") idDoctor: Int,
        @Field("statusDoctor") status: Int,
        @Field("idAppointment") idAppointment: Int
    ):Call<MessageModel>

    @GET("/appointment/medic/{user}")
    fun loadWaitMedic(
        @Header("Authorization") token: String,
        @Path("user") user: String
    ):Call<ObjectModel>

    @GET("/appointment/consults/{user}")
    fun loadConsults(
        @Header("Authorization") token: String,
        @Path("user") user: String
    ):Call<ObjectModel>

    @POST("/appointment/start")
    @FormUrlEncoded
    fun start(
        @Header("Authorization") token: String,
        @Field("idAppointment") idAppointment : Int,
        @Field("idDoctor") idDoctor: Int,
        @Field("dateStart") dateStart: String
    ):Call<MessageModel>

    @DELETE("/appointment/{idAppointment}")
    fun refuse(
        @Header("Authorization") token: String,
        @Path("idAppointment") idAppointment: Int
    ):Call<MessageModel>

    @PATCH("/appointment/finish")
    @FormUrlEncoded
    fun finish(
        @Header("Authorization") token: String,
        @Field("idAppointment") idAppointment: Int,
        @Field("dateFinish") dateFinish: String
    ):Call<MessageModel>
}