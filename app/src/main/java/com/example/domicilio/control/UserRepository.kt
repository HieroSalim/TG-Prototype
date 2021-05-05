package com.example.domicilio.control

import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.listener.APIListenerUser
import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.model.UserModel
import com.example.domicilio.services.repository.RetrofitClient
import com.example.domicilio.services.repository.UserService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository {

    private val mRemote = RetrofitClient.createService(UserService::class.java)

    fun login(login: String, pass: String, listener: APIListener) {
        val call: Call<LoginModel> = mRemote.login(login, pass)
        //Assíncrona
        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.code() != 202) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun add(CPF: String,name: String, email:String, user: String, pass: String, cell: String, typeUser: String, listener: APIListenerUser) {
        val call: Call<UserModel> = mRemote.register(CPF, name, email, user, pass, cell, typeUser)
        //Assíncrona
        call.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.code() != 201) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("error"))
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                listener.onFailure("Ocorre um erro inesperado. Tente novamente mais tarde.")
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