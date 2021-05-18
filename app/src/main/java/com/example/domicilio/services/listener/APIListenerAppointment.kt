package com.example.domicilio.services.listener

import com.example.domicilio.services.model.AppointmentModel
import com.example.domicilio.services.model.UserModel

interface APIListenerAppointment {

    fun onSuccess(model: AppointmentModel)

    fun onFailure(str: String)
}