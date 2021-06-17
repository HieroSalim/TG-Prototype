package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

class LoginModel{

    @SerializedName("auth")
    var auth: Int = 0

    @SerializedName("token")
    var token: String = ""
}