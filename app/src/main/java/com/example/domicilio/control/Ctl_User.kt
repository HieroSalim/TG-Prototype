package com.example.domicilio.control

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.listener.ValidationListener
import com.example.domicilio.services.model.Login
import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.repository.UserRepository
import java.io.Console


class Ctl_User{
    private val mUserRepository = UserRepository()

    private val mLogin = MutableLiveData<ValidationListener>()
    var login: LiveData<ValidationListener> = mLogin

    fun add(){

    }
    fun disable(){

    }
    fun edit(){

    }
    fun doLogin (user : String, pass : String) {
        mUserRepository.login(user, pass, object : APIListener {
            override fun onSuccess(model: LoginModel) {
                mLogin.value = ValidationListener()
            }
            override fun onFailure(str: String) {
                mLogin.value = ValidationListener(str)
            }
        })
    }
    fun listDoctor(){

    }
    fun setEvalue(){

    }
    fun EvalueAtt(){

    }
    fun getTypeUser(){

    }
}