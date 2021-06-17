package com.example.domicilio.services.repository.remote

import com.example.domicilio.services.model.DoctorModel
import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.model.ProfileModel
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface DoctorService {

    @GET("index.php")
    fun validate(@QueryMap filter: HashMap<String,String>):Call<ObjectModel>

    @POST("doctor")
    @FormUrlEncoded
    fun register(
        @Header("Authorization") token: String,
        @Field("typeProfessional") typeProfessional : String,
        @Field("professionalId") professionalId: String,
        @Field("specialization") specialization: String,
        @Field("CNH") CNH: String,
        @Field("typeCNH") typeCNH: String,
        @Field("CPF") CPF: String
    ):Call<DoctorModel>

    @GET("doctor/on/{type}/{dateHour}")
    fun searchDoctorsOn(
        @Header("Authorization") token: String,
        @Path("type") typeProfessional: String,
        @Path("dateHour") dateHour: String
    ):Call<ObjectModel>

    @GET("doctor/{id}")
    fun loadProfile(
        @Header("Authorization") token: String,
        @Path("id") idProfile: Int
    ):Call<ProfileModel>

    @POST("doctor/profile")
    @FormUrlEncoded
    fun addProfile(
        @Header("Authorization") token: String,
        @Field("description") description: String,
        @Field("user") user: String,
        @Field("price") price: Float
    ):Call<MessageModel>
}