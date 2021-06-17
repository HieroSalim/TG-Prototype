package com.example.domicilio.control

import android.content.Context
import android.widget.Toast
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.AddressModel
import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.repository.remote.AddressService
import com.example.domicilio.services.repository.remote.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressRepository() {
    val mRemote = RetrofitClient.createService(AddressService::class.java)

    fun add(
        context: Context?,
        token: String,
        street: String,
        neighborhood: String,
        city: String,
        state: String,
        cep: String,
        user: String
    ) {
        val call: Call<MessageModel> = mRemote.add(token, street, neighborhood, city, state, cep, user)
        call.enqueue(object : Callback<MessageModel>{
            override fun onResponse(call: Call<MessageModel>, response: Response<MessageModel>) {
                if(response.code() != 201){
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

    fun edit() {

    }

    fun remove() {

    }

    fun filterByDate() {

    }

    fun listByUser(token: String, user: String, listener: APIListener<ObjectModel>) {
        val call: Call<ObjectModel> = mRemote.getAddress(token, user)

        call.enqueue(object : Callback<ObjectModel> {
            override fun onResponse(call: Call<ObjectModel>, response: Response<ObjectModel>) {
                if (response.code() != 200) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<ObjectModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }
}
