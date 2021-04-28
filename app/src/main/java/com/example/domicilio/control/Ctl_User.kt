package com.example.domicilio.control

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.listener.APIListenerUser
import com.example.domicilio.services.listener.ValidationListener
import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.model.UserModel


class Ctl_User{
    private val mUserRepository = UserRepository()

    private val mLogin = MutableLiveData<ValidationListener>()
    var login: LiveData<ValidationListener> = mLogin

    private val mUser = MutableLiveData<ValidationListener>()
    var user: LiveData<ValidationListener> = mUser

    fun add(CPF: String,name: String, email:String, user: String, pass: String, cell: String, typeUser: String){
        mUserRepository.add(CPF, name, email, user, pass, cell, typeUser, object : APIListenerUser{
            override fun onSuccess(model: UserModel) {
                mUser.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mUser.value = ValidationListener(str)
            }

        })
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