package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

class DoctorModel{
    @SerializedName("numero")
    var CRM: String = ""

    @SerializedName("profissao")
    var specialization: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("price")
    var price: String = ""

    @SerializedName("specialization")
    var funcao: String = ""

    @SerializedName("CNH")
    var CNH: String = ""

    @SerializedName("typeCNH")
    var typeCNH: String =""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("address")
    var address: String = ""

    @SerializedName("cell")
    var cell: String = ""

    @SerializedName("item")
    var item = object {}

    @SerializedName("dados")
    var medicos = object {}
}
