package com.example.domicilio.services.listener

import com.example.domicilio.services.model.UserModel

interface APIListenerUser {
    fun onSuccess(model: UserModel)

    fun onFailure(str: String)
}