package com.example.domicilio.services.listener

import com.example.domicilio.services.model.LoginModel

interface APIListener<T> {

    fun onSuccess(result: T)

    fun onFailure(str: String)

}
