package com.example.domicilio.control

import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.listener.APIListenerAppointment
import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.repository.RetrofitClient
import com.example.domicilio.services.repository.UserService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentRepository {

    private val mRemote = RetrofitClient.createService(UserService::class.java)

    fun load(login: String, pass: String, listener: APIListenerAppointment) {
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
}