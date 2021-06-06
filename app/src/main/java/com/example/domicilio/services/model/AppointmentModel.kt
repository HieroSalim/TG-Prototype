package com.example.domicilio.services.model

import com.google.gson.annotations.SerializedName

data class AppointmentModel(
    @SerializedName("name")
    var name: String,
    @SerializedName("doctors")
    var doctors: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("dateHour")
    var dateHour: String,
    @SerializedName("statusDoctor")
    var status: Boolean)