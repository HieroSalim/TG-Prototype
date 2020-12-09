package com.example.domicilio.services.repository

import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.repository.remote.RetrofitClient
import com.example.domicilio.services.repository.remote.UserService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository {

    private val mRemote = RetrofitClient.createService(UserService::class.java)

    fun login(user: String, pass: String, listener: APIListener) {
        val call: Call<LoginModel> = mRemote.login(user, pass, typeUser = "User")
        //Assíncrona
        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.code() != 201) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("menssagem"))
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                listener.onFailure("Ocorre um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

}