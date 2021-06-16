package com.example.domicilio.control

import android.content.Context
import android.widget.Toast
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.repository.remote.AppointmentService
import com.example.domicilio.services.repository.remote.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentRepository {
    private val mRemote = RetrofitClient.createService(AppointmentService::class.java)

    fun create(
        token: String, user: String, description: String, dateHour: String, doctors: String,
        statusDoctor: Int, fkAddress: Int, listener: APIListener<MessageModel>) {
        val call: Call<MessageModel> =
            mRemote.create(token, user, description, dateHour, doctors, statusDoctor, fkAddress)

        call.enqueue(object : Callback<MessageModel> {
            override fun onResponse(call: Call<MessageModel>, response: Response<MessageModel>) {
                if(response.code() != 201){
                    val jObjError =  JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun loadWait(token: String,user: String, listener: APIListener<ObjectModel>){
        val call: Call<ObjectModel> = mRemote.loadWait(token, user)
        call.enqueue(object : Callback<ObjectModel>{
            override fun onResponse(call: Call<ObjectModel>, response: Response<ObjectModel>) {
                if(response.code() != 200){
                    val jObjError =  JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<ObjectModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun loadAccepts(token: String,user: String, listener: APIListener<ObjectModel>){
        val call: Call<ObjectModel> = mRemote.loadAccepts(token, user)
        call.enqueue(object : Callback<ObjectModel>{
            override fun onResponse(call: Call<ObjectModel>, response: Response<ObjectModel>) {
                if(response.code() != 200){
                    val jObjError =  JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<ObjectModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun loadCompletes(token: String,user: String, listener: APIListener<ObjectModel>){
        val call: Call<ObjectModel> = mRemote.loadCompletes(token, user)
        call.enqueue(object : Callback<ObjectModel>{
            override fun onResponse(call: Call<ObjectModel>, response: Response<ObjectModel>) {
                if(response.code() != 200){
                    val jObjError =  JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<ObjectModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun accept(context: Context, token: String, idAppointment: Int, idDoctor: Int, statusDoctor: Int){
        val call: Call<MessageModel> = mRemote.accept(token, idDoctor, statusDoctor, idAppointment)
        call.enqueue(object : Callback<MessageModel> {
            override fun onResponse(call: Call<MessageModel>, response: Response<MessageModel>) {
                if(response.code() != 200){
                    Toast.makeText(context, "Ocorreu um erro inesperado. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
                }else{
                    response.body()?.let {
                        Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                Toast.makeText(context, "Ocorreu um erro inesperado. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}