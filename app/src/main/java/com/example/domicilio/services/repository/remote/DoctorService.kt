package com.example.domicilio.services.repository.remote

import com.example.domicilio.services.model.DoctorModel
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface DoctorService {

    @GET("index.php")
    fun validate(@QueryMap filter: HashMap<String,String>):Call<DoctorModel>

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
    ):Call<DoctorModel>

    @GET("doctor/{id}")
    fun loadProfile(
        @Header("Authorization") token: String,
        @Path("id") idProfile: Int
    ):Call<DoctorModel>
}