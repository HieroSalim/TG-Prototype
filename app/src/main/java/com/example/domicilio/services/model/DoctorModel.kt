package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

class DoctorModel{
    @SerializedName("numero")
    var CRM: String = ""

    @SerializedName("profissao")
    var specialization: String = ""

    @SerializedName("CNH")
    var CNH: String = ""

    @SerializedName("typeCNH")
    var typeCNH: String =""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("cell")
    var cell: String = ""
}
