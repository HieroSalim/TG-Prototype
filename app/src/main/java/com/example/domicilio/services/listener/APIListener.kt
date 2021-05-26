package com.example.domicilio.services.listener

import com.example.domicilio.services.model.LoginModel

interface APIListener {

    fun onSuccess(model: LoginModel)

    fun onFailure(str: String)

}
