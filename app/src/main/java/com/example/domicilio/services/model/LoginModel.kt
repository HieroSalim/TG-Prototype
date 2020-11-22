package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

class LoginModel{

    @SerializedName("user")
    var user: String = ""

    @SerializedName("pass")
    var pass: String = ""

    @SerializedName("typeUser")
    var typeUser: String = ""
}