package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

class UserModel{
    @SerializedName("name")
    var name: String = ""

    @SerializedName("email")
    var email: String = ""

    @SerializedName("cell")
    var cell: String = ""

    @SerializedName("CPF")
    var CPF: String = ""

    @SerializedName("user")
    var user : String = ""

    @SerializedName("pass")
    var pass : String = ""

    @SerializedName("typeUser")
    var typeUser: String = ""
}