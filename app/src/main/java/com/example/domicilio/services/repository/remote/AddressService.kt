package com.example.domicilio.services.repository.remote

import com.example.domicilio.services.model.AddressModel
import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.model.ObjectModel
import retrofit2.Call
import retrofit2.http.*

interface AddressService {

    @GET("address/{user}")
    fun getAddress(
        @Header("Authorization") token: String,
        @Path("user") user: String
    ):Call<ObjectModel>

    @POST("address")
    @FormUrlEncoded
    fun add(
        @Header("Authorization") token: String,
        @Field("street") street: String,
        @Field("neighborhood") neighborhood: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("cep") cep: String,
        @Field("user") user: String
    ):Call<MessageModel>
}