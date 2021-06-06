package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

data class AddressModel (
    @SerializedName("street")
    var street: String,
    @SerializedName("idAddress")
    var idAddress: Int
){
    override fun toString(): String {
        return street
    }
}