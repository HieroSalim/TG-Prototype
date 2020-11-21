package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

//data class Login(var user: String, var pass: String, var typeUser: String)

class Login{

    @SerializedName("user")
    var user: String = ""

    @SerializedName("pass")
    var pass: String = ""

    @SerializedName("typeUser")
    var typeUser: String = ""
}