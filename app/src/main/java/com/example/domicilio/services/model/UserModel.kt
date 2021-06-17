package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

class UserModel{
    @SerializedName("CPF")
    var CPF: String = ""
    @SerializedName("user")
    var user: String = ""
    @SerializedName("typeUser")
    var typeUser: String = ""
    @SerializedName("email")
    var email: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("cell")
    var cell: String = ""
}