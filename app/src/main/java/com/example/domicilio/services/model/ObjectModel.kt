package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

data class ObjectModel(
    @SerializedName("item")
    var item: Any = object {},
    @SerializedName("dados")
    var dados: Any = object {}
)