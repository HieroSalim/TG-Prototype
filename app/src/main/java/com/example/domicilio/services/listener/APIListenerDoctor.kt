package com.example.domicilio.services.listener

import com.example.domicilio.services.model.DoctorModel

interface APIListenerDoctor {

    fun onSuccess(model: DoctorModel)

    fun onFailure(msg: String)
}