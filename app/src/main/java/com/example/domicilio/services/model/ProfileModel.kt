package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

data class ProfileModel (
    @SerializedName("description")
    var description: String = "",
    @SerializedName("price")
    var price: String = "",
    @SerializedName("specialization")
    var funcao: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("idDoctor")
    var idDoctor: Int = 0
)