package com.example.domicilio.services.listener

import com.example.domicilio.services.model.DoctorModel

interface APIListenerValidate {
    fun onSuccess(model: DoctorModel)

    fun onFailure(string: String)
}