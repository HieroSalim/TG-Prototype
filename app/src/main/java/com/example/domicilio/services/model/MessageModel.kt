package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

data class MessageModel (
    @SerializedName("mensagem")
    var msg: String = ""
)