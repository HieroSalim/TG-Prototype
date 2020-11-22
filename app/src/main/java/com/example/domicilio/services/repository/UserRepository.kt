package com.example.domicilio.services.repository

import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.repository.remote.RetrofitClient
import com.example.domicilio.services.repository.remote.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private val mRemote = RetrofitClient.createService(UserService::class.java)

    fun login(user: String, pass: String, typeUser: String){
        val call: Call<LoginModel> = mRemote.login(user,pass,typeUser)
        //Assíncrona
        call.enqueue(object : Callback<LoginModel>{
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                val user = response.body()
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                val s = ""
            }

        })
    }

}